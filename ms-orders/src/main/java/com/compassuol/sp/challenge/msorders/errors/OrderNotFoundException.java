package com.compassuol.sp.challenge.msorders.errors;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
