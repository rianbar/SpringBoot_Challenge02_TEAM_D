package com.compassuol.sp.challenge.msorders.exception;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseErrorTemplate {

    private int code;
    private String status;
    private String message;

    public ResponseErrorTemplate(int code, String status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }
}
