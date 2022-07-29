package com.belong.telecomdirectory.controller;

import com.belong.telecomdirectory.exception.NotFoundException;
import com.belong.telecomdirectory.model.domain.Customer;
import com.belong.telecomdirectory.model.domain.PhoneActivationChangeRequest;
import com.belong.telecomdirectory.service.PhoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping(path = "/phone-numbers")
    public ResponseEntity<Page<Customer>> getPhoneNumbersForAllCustomers(Pageable pageable) {
        return ResponseEntity.ok(phoneService.getPhoneNumbersForAllCustomers(pageable));
    }

    @GetMapping(path = "/{id}/phone-numbers")
    public ResponseEntity<Customer> getPhoneNumberForCustomer(@PathVariable("id") Long id) {
        var customer = phoneService.getPhoneNumbersForCustomer(id);
        return ResponseEntity.ok(customer);
    }

    @PutMapping(path = "/phone-number/activation")
    public ResponseEntity<Customer> activatePhoneNumber(@RequestBody PhoneActivationChangeRequest request) {
        return ResponseEntity.ok(phoneService.changePhoneNumberActiveStatus(request));
    }

}
