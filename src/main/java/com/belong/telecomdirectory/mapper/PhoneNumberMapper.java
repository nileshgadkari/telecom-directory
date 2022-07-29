package com.belong.telecomdirectory.mapper;

import com.belong.telecomdirectory.model.entity.PhoneNumber;

import java.util.List;
import java.util.stream.Collectors;

public class PhoneNumberMapper {

    public static com.belong.telecomdirectory.model.domain.PhoneNumber toDomain(PhoneNumber phoneNumber) {
        return com.belong.telecomdirectory.model.domain.PhoneNumber.builder()
                .number(phoneNumber.getNumber())
                .active(phoneNumber.isActive())
                .build();
    }

    public static List<com.belong.telecomdirectory.model.domain.PhoneNumber> toDomain(List<PhoneNumber> phoneNumbers) {
        return phoneNumbers
                .stream()
                .map(phoneNumber -> toDomain(phoneNumber))
                .collect(Collectors.toList());
    }
}
