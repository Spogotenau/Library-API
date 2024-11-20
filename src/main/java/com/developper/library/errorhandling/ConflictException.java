package com.developper.library.errorhandling;

public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
