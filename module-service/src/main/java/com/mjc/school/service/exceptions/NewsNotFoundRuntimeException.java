package com.mjc.school.service.exceptions;

public class NewsNotFoundRuntimeException extends RuntimeException{
    public NewsNotFoundRuntimeException(String message) {
        super("ERROR 1001 :"+message);
    }
}
