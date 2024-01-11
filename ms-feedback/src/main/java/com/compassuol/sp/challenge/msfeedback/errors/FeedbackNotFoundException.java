package com.compassuol.sp.challenge.msfeedback.errors;

public class FeedbackNotFoundException extends RuntimeException{
    public FeedbackNotFoundException() {
    }

    public FeedbackNotFoundException(String message) {
        super(message);
    }
}
