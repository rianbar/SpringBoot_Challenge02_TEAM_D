package com.compassuol.sp.challenge.msproducts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessRuleException extends RuntimeException{

  private static final String MESSAGE = "Somenthin went wrong in your request";

    public BusinessRuleException() {
      super(MESSAGE);
  }
}
