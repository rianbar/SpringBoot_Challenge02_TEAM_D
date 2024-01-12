package com.compassuol.sp.challenge.msorders.errors;

public class OrderCancelNotAllowedException extends RuntimeException {
    public OrderCancelNotAllowedException(String message) {
        super(message);
    }
}
