package com.projetweb.hydroinfo.controllers;

import com.projetweb.hydroinfo.exceptions.CustomValidationException;
import com.projetweb.hydroinfo.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(CustomValidationException ex) {
        Map<String, String> response = new HashMap<>();
        List<String> errors = ex.getErrors();
        int i = 0;
        for (String error : errors) {
            response.put(Integer.toString(i), error);
            i++;
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception
    ) {
        Map<String, String> response = new HashMap<>();

        List<ObjectError> errors = exception.getBindingResult()
                .getAllErrors();

        for (ObjectError error : errors) {
            FieldError fieldError = (FieldError) error;
            response.put(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value())
                .body(response);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}

