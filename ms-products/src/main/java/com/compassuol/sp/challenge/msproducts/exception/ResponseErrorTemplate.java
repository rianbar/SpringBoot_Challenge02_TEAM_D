package com.compassuol.sp.challenge.msproducts.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorTemplate {

    private int code;
    private String status;
    private String message;
}
