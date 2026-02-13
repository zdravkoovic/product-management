package com.e_commerce.Application.Errors;

import lombok.Getter;

@Getter
public class ProductPurchaseException extends RuntimeException {
    private final String message;

    public ProductPurchaseException(String message) {
        this.message = message;
    }
}
