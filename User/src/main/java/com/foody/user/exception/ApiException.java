package com.foody.user.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}
