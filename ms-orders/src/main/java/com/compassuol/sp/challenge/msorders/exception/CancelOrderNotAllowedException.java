package com.compassuol.sp.challenge.msorders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CancelOrderNotAllowedException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Order cancel not allowed";

  public CancelOrderNotAllowedException() {
    super(DEFAULT_MESSAGE);
  }
}
