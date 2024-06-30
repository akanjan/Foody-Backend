package com.foody.menu.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}
