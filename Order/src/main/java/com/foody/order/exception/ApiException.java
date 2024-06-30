package com.foody.order.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message) {
        super(message);
    }
}
