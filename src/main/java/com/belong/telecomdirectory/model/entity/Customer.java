package com.belong.telecomdirectory.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @ElementCollection
    @CollectionTable(name = "phone_numbers")
    private List<PhoneNumber> phoneNumbers;
}
