package com.bonaiva.app.integration.exception;

import org.springframework.http.HttpStatus;

public class GetAddressException extends IntegrationException {

    private static final String MESSAGE = "Failed to retrieve address";

    public GetAddressException() {
        super(MESSAGE);
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
