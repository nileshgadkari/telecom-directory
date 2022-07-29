package com.belong.telecomdirectory.controller;

import com.belong.telecomdirectory.model.domain.Customer;
import com.belong.telecomdirectory.model.domain.PhoneActivationChangeRequest;
import com.belong.telecomdirectory.service.PhoneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PhoneControllerTest {

    @Mock
    private PhoneService phoneService;
    @Mock
    private Page<Customer> customerPhoneNumbers;
    @Mock
    private Pageable pageable;
    @Mock
    private PhoneActivationChangeRequest phoneActivationChangeRequest;
    @InjectMocks
    private PhoneController phoneController;

    @Test
    void getPhoneNumbersForAllCustomers() {
        when(phoneService.getPhoneNumbersForAllCustomers(any(Pageable.class)))
                .thenReturn(customerPhoneNumbers);

        var response = phoneController.getPhoneNumbersForAllCustomers(pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getPhoneNumberForCustomer() {
        when(phoneService.getPhoneNumbersForCustomer(anyLong()))
                .thenReturn(Customer.builder().build());

        var response = phoneController.getPhoneNumberForCustomer(1l);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void activatePhoneNumber() {
        when(phoneService.changePhoneNumberActiveStatus(phoneActivationChangeRequest))
                .thenReturn(Customer.builder().build());

        var response = phoneController.activatePhoneNumber(phoneActivationChangeRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}