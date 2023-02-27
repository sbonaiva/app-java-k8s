package com.bonaiva.app.integration.exception;

import org.springframework.http.HttpStatus;

public class GetCustomerException extends IntegrationException {

    private static final String MESSAGE = "Failed to retrieve customer";

    public GetCustomerException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
