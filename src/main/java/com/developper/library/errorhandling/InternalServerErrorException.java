package com.developper.library.errorhandling;

public class InternalServerErrorException extends RuntimeException {
    public InternalServerErrorException(String message) {
        super("Internal Server Error: " + message);
    }
}
