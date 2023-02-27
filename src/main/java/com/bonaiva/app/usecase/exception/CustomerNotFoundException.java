package com.bonaiva.app.usecase.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BusinessException {

    private static final String MESSAGE = "User not found: %s";

    public CustomerNotFoundException(final Long id) {
        super(String.format(MESSAGE, id));
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
