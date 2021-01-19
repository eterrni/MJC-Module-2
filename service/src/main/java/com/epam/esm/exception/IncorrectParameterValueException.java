package com.epam.esm.exception;

public class IncorrectParameterValueException extends RuntimeException {
    public IncorrectParameterValueException() {
    }

    public IncorrectParameterValueException(String message) {
        super(message);
    }

    public IncorrectParameterValueException(String message, Throwable cause) {
        super(message, cause);
    }
}
