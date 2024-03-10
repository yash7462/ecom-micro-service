package com.product.config.anotation;

import lombok.Getter;

@Getter
public class AuthValidationException extends RuntimeException {

    public AuthValidationException() {
        super();
    }

    public AuthValidationException(String message) {
        super(message);
    }

    public AuthValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthValidationException(Throwable cause) {
        super(cause);
    }
}
