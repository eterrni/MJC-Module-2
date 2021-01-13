package com.epam.esm.exception_handling;

public class NoSuchGiftCertificateException extends RuntimeException {
    public NoSuchGiftCertificateException(String message) {
        super(message);
    }

    public NoSuchGiftCertificateException(Throwable cause) {
        super(cause);
    }

    public NoSuchGiftCertificateException(String message, Throwable cause) {
        super(message, cause);
    }
}
