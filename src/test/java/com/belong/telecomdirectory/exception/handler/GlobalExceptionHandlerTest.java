package com.belong.telecomdirectory.exception.handler;

import com.belong.telecomdirectory.exception.NotFoundException;
import com.belong.telecomdirectory.exception.PhoneServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GlobalExceptionHandlerTest {

    @Mock
    private ConversionFailedException conversionFailedException;
    @Mock
    private NotFoundException notFoundException;
    @Mock
    private PhoneServiceException phoneServiceException;
    private GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleConnversion() {
        when(conversionFailedException.getMessage()).thenReturn("Bad request");

        var response = globalExceptionHandler.handleConnversion(conversionFailedException);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void handleNotFoundException() {
        when(notFoundException.getMessage()).thenReturn("Not found");

        var response = globalExceptionHandler.handleNotFoundException(notFoundException);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void handleInternalServerError() {
        var response = globalExceptionHandler.handleInternalServerError(phoneServiceException);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}