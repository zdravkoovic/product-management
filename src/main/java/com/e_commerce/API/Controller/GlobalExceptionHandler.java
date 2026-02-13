package com.e_commerce.API.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.e_commerce.Application.Errors.UserMessageException;
import com.e_commerce.Application.Errors.Translators.ApplicationTranslatorException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<UserMessageException> handleAllExceptions(Exception ex) {
        UserMessageException userError = ApplicationTranslatorException.translate(ex);
        // map error codes to HTTP status (optional)
        HttpStatus status = mapErrorCodeToStatus(userError.getHttpStatus());
        return new ResponseEntity<>(userError, status);
    }

    private HttpStatus mapErrorCodeToStatus(Integer httpStatus) {
        return switch (httpStatus) {
            case 422 -> HttpStatus.UNPROCESSABLE_ENTITY;
            case 503 -> HttpStatus.SERVICE_UNAVAILABLE;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
