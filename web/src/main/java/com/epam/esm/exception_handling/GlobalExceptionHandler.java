package com.epam.esm.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<GiftCertificateIncorrectData> handleException(NoSuchGiftCertificateException exception) {
        GiftCertificateIncorrectData giftCertificateIncorrectData = new GiftCertificateIncorrectData();
        giftCertificateIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(giftCertificateIncorrectData, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<GiftCertificateIncorrectData> handleException(Exception exception) {
        GiftCertificateIncorrectData giftCertificateIncorrectData = new GiftCertificateIncorrectData();
        giftCertificateIncorrectData.setInfo(exception.getMessage());
        return new ResponseEntity<>(giftCertificateIncorrectData, HttpStatus.BAD_REQUEST);
    }

}
