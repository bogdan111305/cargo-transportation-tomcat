package com.example.cargo_transportation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityConversionException extends RuntimeException{
    public EntityConversionException(String message){
        super(message);
    }
}