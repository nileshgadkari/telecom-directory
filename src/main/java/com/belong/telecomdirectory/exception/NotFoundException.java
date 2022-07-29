package com.belong.telecomdirectory.exception;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String messsage) {
        super(messsage);
    }

    public NotFoundException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
