package com.bonaiva.app.integration.exception;

import org.springframework.http.HttpStatus;

public class CreateCustomerException extends IntegrationException {

    private static final String MESSAGE = "Failed to create customer";

    public CreateCustomerException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
