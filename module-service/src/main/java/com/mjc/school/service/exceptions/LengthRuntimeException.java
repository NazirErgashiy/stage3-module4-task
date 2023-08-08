package com.mjc.school.service.exceptions;

public class LengthRuntimeException extends RuntimeException{
    public LengthRuntimeException(String message) {
        super("ERROR 1002: "+message);
    }
}
