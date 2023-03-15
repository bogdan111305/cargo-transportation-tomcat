package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.exception.SessionJWTException;
import com.example.cargo_transportation.modal.payload.response.ResponseError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = SessionJWTException.class)
    public ResponseEntity<Object> handleTokenRefreshException(SessionJWTException ex) {
        ResponseError responseError = new ResponseError("Error verify expiration", ex.getMessage());
        return new ResponseEntity(responseError, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleUserExist(EntityNotFoundException e) {
        ResponseError responseError = new ResponseError("Please check credentials", e.getMessage());
        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleUsernameNotFound(UsernameNotFoundException e) {
        ResponseError responseError = new ResponseError("Please check username", e.getMessage());
        return new ResponseEntity(responseError, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                               HttpHeaders headers, HttpStatusCode status,
                                                               WebRequest webRequest){
        ResponseError responseError = new ResponseError("Malformed JSON Request", ex.getMessage());
        return new ResponseEntity(responseError, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ResponseError responseError = new ResponseError(
                "Please check the data to be filled in",
                "Validation failed",
                ex.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(x -> x.getDefaultMessage())
                        .collect(Collectors.toList())
        );
        return new ResponseEntity<>(responseError, status);
    }
}

