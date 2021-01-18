package com.epam.esm.exception;

public class NotExistIdEntityException extends RuntimeException {
    public NotExistIdEntityException() {
    }

    public NotExistIdEntityException(String message) {
        super(message);
    }

    public NotExistIdEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
