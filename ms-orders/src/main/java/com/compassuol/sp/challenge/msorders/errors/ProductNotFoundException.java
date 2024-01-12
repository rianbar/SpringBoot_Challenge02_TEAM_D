package com.compassuol.sp.challenge.msorders.errors;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
