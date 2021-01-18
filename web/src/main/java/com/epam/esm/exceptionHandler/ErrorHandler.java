package com.epam.esm.exceptionHandler;

public class ErrorHandler {
    private String errorMessage;
    private int errorCode;

    public ErrorHandler(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

    public ErrorHandler(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
