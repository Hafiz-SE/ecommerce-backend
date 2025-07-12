package com.wsd.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class BadCredentialException extends RuntimeException {
    private final String errorCode;

    public BadCredentialException(String errorCode) {
        this.errorCode = errorCode;
    }
}
