package com.compassuol.sp.challenge.msfeedback.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorTemplate {

    private int code;
    private String status;
    private String message;
}
