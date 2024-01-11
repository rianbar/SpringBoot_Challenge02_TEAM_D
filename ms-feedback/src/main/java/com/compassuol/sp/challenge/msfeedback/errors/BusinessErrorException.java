package com.compassuol.sp.challenge.msfeedback.errors;

public class BusinessErrorException extends RuntimeException{
    public BusinessErrorException() {
    }

    public BusinessErrorException(String message) {
        super(message);
    }
}
