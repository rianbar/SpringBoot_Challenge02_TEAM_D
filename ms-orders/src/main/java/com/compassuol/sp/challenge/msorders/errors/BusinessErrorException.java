package com.compassuol.sp.challenge.msorders.errors;

public class BusinessErrorException extends RuntimeException{

    public BusinessErrorException(String message) {
        super(message);
    }
}
