package com.epam.esm.exception_handling;

public class ErrorHandler {
    private final String errorMessage;
    private final int errorCode;

    public ErrorHandler(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
