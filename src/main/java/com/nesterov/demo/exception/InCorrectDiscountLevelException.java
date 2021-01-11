package com.nesterov.demo.exception;

public class InCorrectDiscountLevelException extends RuntimeException {

    public InCorrectDiscountLevelException(String message) {
        super(message);
    }

    public InCorrectDiscountLevelException(String message, Throwable cause) {
        super(message, cause);
    }
}
