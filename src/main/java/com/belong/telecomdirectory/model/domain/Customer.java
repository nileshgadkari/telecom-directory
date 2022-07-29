package com.belong.telecomdirectory.model.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@Builder
@EqualsAndHashCode
public class Customer {

    private String firstName;

    private String lastName;

    private List<PhoneNumber> phoneNumbers;

}
