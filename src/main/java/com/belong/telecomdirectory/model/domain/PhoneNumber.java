package com.belong.telecomdirectory.model.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode
public class PhoneNumber {

    private String number;

    private boolean active;

}
