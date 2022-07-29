package com.belong.telecomdirectory.service;

import com.belong.telecomdirectory.dao.PhoneRepository;
import com.belong.telecomdirectory.exception.NotFoundException;
import com.belong.telecomdirectory.exception.PhoneServiceException;
import com.belong.telecomdirectory.model.domain.Customer;
import com.belong.telecomdirectory.model.domain.PhoneActivationChangeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.belong.telecomdirectory.mapper.CustomerMapper.toDomain;

@Service
@RequiredArgsConstructor
@Slf4j
public class PhoneService {

    private final PhoneRepository phoneRepository;

    public Page<Customer> getPhoneNumbersForAllCustomers(Pageable pageable) {
        try {
            var phoneNumbers = phoneRepository.findAll(pageable);
            return phoneNumbers.map(phNumbers -> toDomain(phNumbers));
        }catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new PhoneServiceException(ex.getMessage(), ex);
        }
    }

    @Transactional
    public Customer changePhoneNumberActiveStatus(PhoneActivationChangeRequest request) {
        try {
            var customer = phoneRepository.findById(request.getCustomerId());
            return customer.map(c -> handlePhoneActivationChange(c, request))
                    .orElseThrow(() -> logErrorAndGetNotFoundException("Customer with id:%s not found", request.getCustomerId()));
        }catch(NotFoundException nfe) {
            throw nfe;
        }catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new PhoneServiceException(ex.getMessage(), ex);
        }
    }

    public Customer getPhoneNumbersForCustomer(Long id) {
        try {
            var customer = phoneRepository.findById(id);
            return customer.map(c -> toDomain(c))
                    .orElseThrow(() -> logErrorAndGetNotFoundException("Customer with id:%s not found", id));
        }catch(NotFoundException nfe) {
            throw nfe;
        }catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            throw new PhoneServiceException(ex.getMessage(), ex);
        }
    }

    private Customer handlePhoneActivationChange(com.belong.telecomdirectory.model.entity.Customer c,
                                                 PhoneActivationChangeRequest request) {
        c.getPhoneNumbers()
                .stream()
                .filter(ph -> ph.getNumber().equals(request.getPhoneNumber()))
                .findFirst()
                .ifPresentOrElse(ph -> ph.setActive(request.getActivationStatus()),
                        () -> {
                            var ex = logErrorAndGetNotFoundException(
                                    "Phone number:%s not found for customer id:%s",
                                    request.getPhoneNumber(), c.getId());
                            throw ex;
                        });

        var savedCustomer =  phoneRepository.save(c);
        return toDomain(savedCustomer);
    }

    private NotFoundException logErrorAndGetNotFoundException(String message, Object... args) {
        var errorMessage = String.format(message, args);
        log.info(errorMessage);
        return new NotFoundException(errorMessage);
    }
}
