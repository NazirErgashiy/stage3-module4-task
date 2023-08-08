package com.mjc.school.service.exceptions;

public class AuthorNotFoundRuntimeException extends RuntimeException{
    public AuthorNotFoundRuntimeException(String message) {
        super("ERROR 1001: "+message);
    }
}
