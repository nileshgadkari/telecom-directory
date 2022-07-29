package com.belong.telecomdirectory.service;

import com.belong.telecomdirectory.dao.PhoneRepository;
import com.belong.telecomdirectory.exception.NotFoundException;
import com.belong.telecomdirectory.exception.PhoneServiceException;
import com.belong.telecomdirectory.mapper.CustomerMapper;
import com.belong.telecomdirectory.mapper.PhoneNumberMapper;
import com.belong.telecomdirectory.model.domain.PhoneActivationChangeRequest;
import com.belong.telecomdirectory.model.entity.Customer;
import com.belong.telecomdirectory.model.entity.PhoneNumber;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;
    @Mock
    private Pageable pageable;
    @InjectMocks
    private PhoneService phoneService;

    @Test
    void getPhoneNumbersForAllCustomers() {
        when(phoneRepository.findAll(pageable)).thenReturn(new PageImpl<>(getCustomerPhoneNumbers()));
        var phoneNumbers = phoneService.getPhoneNumbersForAllCustomers(pageable);
        assertAll(() -> assertEquals(2, phoneNumbers.getSize()),
                  () ->  expectedCustomerAndPhoneNumbersAreReturned(phoneNumbers));
    }

    @Test
    void getPhoneNumbersForAllCustomers_ExceptionIsThrown() {
        doThrow(PhoneServiceException.class).when(phoneRepository).findAll(pageable);
        assertThrows(PhoneServiceException.class, () -> phoneService.getPhoneNumbersForAllCustomers(pageable));
    }

    @Test
    void changePhoneNumberActiveStatus() {
        PhoneActivationChangeRequest request = new PhoneActivationChangeRequest();
        request.setCustomerId(2l);
        request.setActivationStatus(false);
        request.setPhoneNumber("123-888-1111");

        var customerPhoneNumbers = getCustomerPhoneNumbers();

        when(phoneRepository.findById(anyLong())).thenReturn(Optional.of(customerPhoneNumbers.get(1)));
        when(phoneRepository.save(any())).thenReturn(customerPhoneNumbers.get(1));

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
        phoneService.changePhoneNumberActiveStatus(request);

        verify(phoneRepository).save(argumentCaptor.capture());
        Customer customer = argumentCaptor.getValue();
        assertFalse(customer.getPhoneNumbers().get(1).isActive());
    }

    @Test
    void changePhoneNumberActiveStatus_CustomerIsNotFound() {
        PhoneActivationChangeRequest request = new PhoneActivationChangeRequest();
        request.setCustomerId(2l);
        request.setActivationStatus(false);
        request.setPhoneNumber("123-888-1111");

        when(phoneRepository.findById(anyLong())).thenReturn(Optional.empty());

        var notFoundException = assertThrows(NotFoundException.class, () -> phoneService.changePhoneNumberActiveStatus(request));

        assertEquals("Customer with id:2 not found", notFoundException.getMessage());
    }

    @Test
    void changePhoneNumberActiveStatus_CustomerPhoneNumberIsNotFound() {
        PhoneActivationChangeRequest request = new PhoneActivationChangeRequest();
        request.setCustomerId(2l);
        request.setActivationStatus(false);
        request.setPhoneNumber("777-777-7777");

        var customerPhoneNumbers = getCustomerPhoneNumbers();

        when(phoneRepository.findById(anyLong())).thenReturn(Optional.of(customerPhoneNumbers.get(1)));

        var notFoundException = assertThrows(NotFoundException.class, () -> phoneService.changePhoneNumberActiveStatus(request));

        assertEquals("Phone number:777-777-7777 not found for customer id:2", notFoundException.getMessage());
    }

    @Test
    void changePhoneNumberActiveStatus_ExceptionIsThrown() {
        doThrow(PhoneServiceException.class).when(phoneRepository).findById(1l);

        var request = new PhoneActivationChangeRequest();
        request.setCustomerId(1l);

        assertThrows(PhoneServiceException.class, () -> phoneService.changePhoneNumberActiveStatus(request));
    }

    @Test
    void getPhoneNumbersForCustomer() {
        var customerPhoneNumbers = getCustomerPhoneNumbers();
        var customer1PhoneNumbers = PhoneNumberMapper.toDomain(customerPhoneNumbers.get(0).getPhoneNumbers());

        when(phoneRepository.findById(1l)).thenReturn(Optional.of(customerPhoneNumbers.get(0)));

        var customer = phoneService.getPhoneNumbersForCustomer(1l);

        assertAll(() -> assertEquals(2, customer.getPhoneNumbers().size()),
                () ->  customer.getPhoneNumbers().stream()
                        .forEach(ph -> assertTrue(customer1PhoneNumbers.contains(ph))));
    }

    @Test
    void getPhoneNumbersForCustomer_CustomerIsNotFound() {
        when(phoneRepository.findById(1l)).thenReturn(Optional.empty());

        var notFoundException =
                assertThrows(NotFoundException.class, () -> phoneService.getPhoneNumbersForCustomer(1l));

        assertEquals("Customer with id:1 not found", notFoundException.getMessage());
    }

    @Test
    void getPhoneNumbersForCustomer_ExceptionIsThrown() {
        doThrow(PhoneServiceException.class).when(phoneRepository).findById(1l);
        assertThrows(PhoneServiceException.class, () -> phoneService.getPhoneNumbersForCustomer(1l));
    }

    private List<Customer> getCustomerPhoneNumbers() {
         Customer customer1 = new Customer();
         customer1.setId(1l);
         customer1.setFirstName("Tom");
         customer1.setLastName("C");
         customer1.setPhoneNumbers(getPhoneNumbers("123-987-8767", "111-222-3333"));

        Customer customer2 = new Customer();
        customer2.setId(2l);
        customer2.setFirstName("Elon");
        customer2.setLastName("M");
        customer2.setPhoneNumbers(getPhoneNumbers("444-111-9999", "123-888-1111"));

         return List.of(customer1,customer2);
    }

    private List<PhoneNumber> getPhoneNumbers(String... phoneNumbers) {
        List<PhoneNumber> phNumbers = new ArrayList<>();

        Arrays.stream(phoneNumbers).forEach(ph -> {
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setNumber(ph);
            phoneNumber.setActive(true);
            phNumbers.add(phoneNumber);
        });
        return phNumbers;
    }

    private void expectedCustomerAndPhoneNumbersAreReturned(Page<com.belong.telecomdirectory.model.domain.Customer> phoneNumbers) {
        List<com.belong.telecomdirectory.model.domain.Customer> customers = CustomerMapper.toDomain(getCustomerPhoneNumbers());
        phoneNumbers.stream().forEach(c -> assertTrue(customers.contains(c)));
    }

}