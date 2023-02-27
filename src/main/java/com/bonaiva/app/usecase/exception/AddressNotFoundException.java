package com.bonaiva.app.usecase.exception;

import org.springframework.http.HttpStatus;

public class AddressNotFoundException extends BusinessException {

    private static final String MESSAGE = "Address not found for postal code: %s";

    public AddressNotFoundException(final String postalCode) {
        super(String.format(MESSAGE, postalCode));
    }

    @Override
    public HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
