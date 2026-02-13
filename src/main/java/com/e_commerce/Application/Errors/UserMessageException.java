package com.e_commerce.Application.Errors;

import lombok.Getter;

@Getter
public class UserMessageException {
    private final String code;        // unique error code, e.g., PRODUCT_NOT_FOUND
    private final String message;     // user-friendly message
    private final Integer httpStatus;
    private final Object details;     // optional, like invalid field name, DB info, etc.

    public UserMessageException(String code, String message, Integer httpStatus) {
        this(code, message, httpStatus, null);
    }

    public UserMessageException(String code, String message, Integer httpStatus, Object details) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
        this.details = details;
    }
}
