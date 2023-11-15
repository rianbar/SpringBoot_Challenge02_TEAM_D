package com.compassuol.sp.challenge.msproducts.exception;

import com.compassuol.sp.challenge.msproducts.exception.type.BusinessErrorException;
import com.compassuol.sp.challenge.msproducts.exception.type.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> notFoundProductErrors(ProductNotFoundException ex) {
        String message = ex.getMessage();
        var response = new ResponseErrorTemplate(404, "NOT_FOUND", message);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> dataNotValidException(MethodArgumentNotValidException ex) {
        String message = Objects.requireNonNull(ex.getFieldError()).getDefaultMessage();
        var response = new ResponseErrorTemplate(400, "BAD_REQUEST", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(BusinessErrorException.class)
    public ResponseEntity<Object> violatedBusinessConstraint(BusinessErrorException ex) {
        String message = ex.getMessage();
        var response = new ResponseErrorTemplate(400, "BAD_REQUEST", message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeException(RuntimeException ex) {
        String message = ex.getMessage();
        var response = new ResponseErrorTemplate(500, "INTERNAL_SERVER_ERROR", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
