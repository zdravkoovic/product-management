package com.e_commerce.Application.Errors.Translators;

import java.lang.reflect.Type;

import org.springframework.dao.DataIntegrityViolationException;

import com.e_commerce.Application.Errors.ProductPurchaseException;
import com.e_commerce.Application.Errors.UserMessageException;
import com.e_commerce.Core.Errors.DomainException;

public class ApplicationTranslatorException {
        public static UserMessageException translate(Throwable ex) {
        if (ex instanceof DataIntegrityViolationException) {
            return new UserMessageException("DB_CONSTRAINT_VIOLATION", "Database constraint violated", 503);
        }
        if (ex instanceof IllegalArgumentException) {
            return new UserMessageException("INVALID_ARGUMENT", ex.getMessage(), 422);
        }
        if (ex instanceof DomainException) {
            return new UserMessageException("INVALID_DOMAIN", ex.getMessage(), 422);
        }
        if (ex instanceof ProductPurchaseException) {
            return new UserMessageException("INVALID_PURCHASE", ex.getMessage(), 422);
        }
        // fallback for unexpected errors
        return new UserMessageException("UNEXPECTED_ERROR", "An unexpected error occurred", 503, ex.getMessage());
    }
}
