package com.company_manager.api.exceptions;

public class GenericNotFoundException extends RuntimeException{
    public GenericNotFoundException(String message) {
        super(message);
    }
}
