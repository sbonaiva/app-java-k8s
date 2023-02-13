package com.bonaiva.app.integration.exception;

import org.springframework.http.HttpStatus;

public abstract class IntegrationException extends RuntimeException {

    protected IntegrationException(final String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();

}
