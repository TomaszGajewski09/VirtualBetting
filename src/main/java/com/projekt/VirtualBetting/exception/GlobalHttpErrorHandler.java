package com.projekt.VirtualBetting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalHttpErrorHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DomainNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(DomainNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage() + " with given ID doesn't exist", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEnumTypeException.class)
    public ResponseEntity<Object> handleInvalidEnumTypeException(InvalidEnumTypeException exception) {
        return new ResponseEntity<>("Invalid type " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingFieldException.class)
    public ResponseEntity<Object> handleMissingFieldException(MissingFieldException exception) {
        return new ResponseEntity<>("Missing field - " + exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationException(ValidationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<Object> handleInsufficientBalanceException(InsufficientBalanceException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
