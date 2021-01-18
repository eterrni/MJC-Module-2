package com.epam.esm.exception;

public class EmptyIdException extends RuntimeException {
    public EmptyIdException() {
    }

    public EmptyIdException(String message) {
        super(message);
    }

    public EmptyIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
