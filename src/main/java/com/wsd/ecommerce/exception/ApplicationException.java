package com.wsd.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApplicationException extends RuntimeException {
    private final String errorCode;

    public ApplicationException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ApplicationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}