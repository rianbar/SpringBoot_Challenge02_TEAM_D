package com.rian.ecommerce.challenge.msorders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UpdateRequestRejectedException extends RuntimeException{

  private static final String DEFAULT_MESSAGE = "The update request was denied due to policy restrictions or invalid data";

  public UpdateRequestRejectedException() {
    super(DEFAULT_MESSAGE);
  }
}
