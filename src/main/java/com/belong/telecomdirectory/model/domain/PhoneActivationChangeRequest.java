package com.belong.telecomdirectory.model.domain;

import lombok.Data;

@Data
public class PhoneActivationChangeRequest {

    private Long customerId;

    private String phoneNumber;

    private Boolean activationStatus;

}
