package com.belong.telecomdirectory.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
@EqualsAndHashCode
public class PhoneNumber {

    @Column
    private String number;

    @Column
    private boolean active;

}
