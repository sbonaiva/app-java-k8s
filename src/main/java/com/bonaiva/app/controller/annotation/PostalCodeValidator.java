package com.bonaiva.app.controller.annotation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;


public class PostalCodeValidator implements ConstraintValidator<PostalCode, String> {

    private static final int POSTAL_CODE_LENGTH = 8;
    private static final String DIGITS_ONLY_REGEX = "[0-9]+";

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        if (StringUtils.hasText(value)) {
            return value.length() == POSTAL_CODE_LENGTH && value.matches(DIGITS_ONLY_REGEX);
        }
        return true;
    }
}
