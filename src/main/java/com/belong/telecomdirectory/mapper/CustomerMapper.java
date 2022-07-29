package com.belong.telecomdirectory.mapper;

import com.belong.telecomdirectory.model.domain.Customer;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static Customer toDomain(com.belong.telecomdirectory.model.entity.Customer customer) {
        return Customer.builder()
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumbers(PhoneNumberMapper.toDomain(customer.getPhoneNumbers()))
                .build();
    }

    public static List<Customer> toDomain(List<com.belong.telecomdirectory.model.entity.Customer> customers) {
       return customers.stream().map(c -> toDomain(c))
               .collect(Collectors.toList());
    }
}
