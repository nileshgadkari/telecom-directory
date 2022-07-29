package com.belong.telecomdirectory.exception;

public class PhoneServiceException extends RuntimeException{

    public PhoneServiceException(String messsage) {
        super(messsage);
    }

    public PhoneServiceException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
