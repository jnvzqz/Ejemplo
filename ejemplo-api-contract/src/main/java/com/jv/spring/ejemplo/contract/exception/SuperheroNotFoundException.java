package com.jv.spring.ejemplo.contract.exception;

public class SuperheroNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public SuperheroNotFoundException(String message) {
        super(message);
    }
}