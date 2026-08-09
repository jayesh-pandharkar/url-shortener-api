package com.jay.urlshortener.exception;

public class UnauthorizedUrlAccessException extends RuntimeException {

    public UnauthorizedUrlAccessException(String message) {
        super(message);
    }
}