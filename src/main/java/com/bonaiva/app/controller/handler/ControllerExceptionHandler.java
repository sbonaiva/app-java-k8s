package com.bonaiva.app.controller.handler;

import com.bonaiva.app.integration.exception.IntegrationException;
import com.bonaiva.app.usecase.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    private static final String GENERIC_ERROR_MESSAGE = "An unexpected error occurred";

    public record ErrorResponse(String message, int code, String path, long timestamp){}

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(final BusinessException e, final WebRequest req) {

        var errorResponse = new ErrorResponse(
                e.getMessage(),
                e.getStatus().value(),
                req.getContextPath(),
                Instant.now().getEpochSecond()
        );
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrationException(final IntegrationException e, final WebRequest req) {

        var errorResponse = new ErrorResponse(
                e.getMessage(),
                e.getStatus().value(),
                req.getContextPath(),
                Instant.now().getEpochSecond()
        );
        return new ResponseEntity<>(errorResponse, e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(final Exception e, final WebRequest req) {

        var errorResponse = new ErrorResponse(
                GENERIC_ERROR_MESSAGE,
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                req.getContextPath(),
                Instant.now().getEpochSecond()
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
