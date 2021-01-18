package com.epam.esm.exceptionHandler;

import com.epam.esm.exception.EmptyIdException;
import com.epam.esm.exception.NotExistIdEntityException;
import com.epam.esm.repository.exception.DuplicateNameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleNotExistIdEntityException(NotExistIdEntityException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleDuplicateNameException(DuplicateNameException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorHandler> handleEmptyIdException(EmptyIdException exception) {
        return new ResponseEntity<>(new ErrorHandler(exception.getMessage(), 40), HttpStatus.BAD_REQUEST);
    }

}
