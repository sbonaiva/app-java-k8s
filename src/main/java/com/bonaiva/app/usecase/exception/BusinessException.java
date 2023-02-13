package com.bonaiva.app.usecase.exception;

import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {

    protected BusinessException(final String message) {
        super(message);
    }

    public abstract HttpStatus getStatus();

}
