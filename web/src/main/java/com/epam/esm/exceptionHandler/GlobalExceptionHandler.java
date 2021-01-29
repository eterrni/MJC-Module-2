package com.epam.esm.exceptionHandler;

import com.epam.esm.exception.IncorrectParameterValueException;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.exception.DuplicateNameException;
import com.epam.esm.repository.exception.NotEnoughDataForRegistrationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleNotExistIdEntityException(NotExistIdEntityException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 30), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleDuplicateNameException(DuplicateNameException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.CONFLICT);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleIncorrectParameterValueException(IncorrectParameterValueException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 50), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleNotEnoughDataForRegistrationException(NotEnoughDataForRegistrationException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 60), HttpStatus.BAD_REQUEST);
    }

}
