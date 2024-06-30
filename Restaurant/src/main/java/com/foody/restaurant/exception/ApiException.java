package com.foody.restaurant.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}
