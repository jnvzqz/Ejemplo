package com.jv.spring.ejemplo.rest.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.jv.spring.ejemplo.contract.exception.SuperheroNotFoundException;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({SuperheroNotFoundException.class})
    public ResponseEntity<Object> handleSuperheroNotFoundException(SuperheroNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> handleRuntimeException(RuntimeException exception) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exception.getMessage());
    }
}