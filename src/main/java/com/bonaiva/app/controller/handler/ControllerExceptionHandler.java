package com.bonaiva.app.controller.handler;

import com.bonaiva.app.integration.exception.IntegrationException;
import com.bonaiva.app.usecase.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final String DEFAULT_ERROR_MESSAGE  = "An unexpected error occurred";
    private static final String VALIDATION_ERROR_MESSAGE  = "Validation failed";


    @ExceptionHandler(BusinessException.class)
    public ProblemDetail handleBusinessException(final BusinessException e) {
        return ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(IntegrationException.class)
    public ProblemDetail handleIntegrationException(final IntegrationException e) {
        return ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
    }

    @ExceptionHandler(BindException.class)
    public ProblemDetail handleMethodArgumentNotValidException(final BindException e) {
        var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, VALIDATION_ERROR_MESSAGE);
        e.getFieldErrors().forEach(fe ->  problemDetail.setProperty(fe.getField(), fe.getDefaultMessage()));
        return problemDetail;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(final Exception e) {
        log.error(DEFAULT_ERROR_MESSAGE, e);
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, DEFAULT_ERROR_MESSAGE);
    }
}
