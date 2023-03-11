package com.example.cargo_transportation.exception;

public class SessionJWTException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private static final String defaultMessage = "Exception occurred while working with the session";

    public SessionJWTException() {
        super(defaultMessage);
    }

    public SessionJWTException(String message) {
        super(message);
    }
}
