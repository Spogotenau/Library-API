package com.developper.library.errorhandling;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super("Bad request: " + message);
    }
}
