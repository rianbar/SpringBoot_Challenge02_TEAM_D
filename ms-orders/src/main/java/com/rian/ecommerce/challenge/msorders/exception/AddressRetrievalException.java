package com.rian.ecommerce.challenge.msorders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_GATEWAY)
public class AddressRetrievalException extends RuntimeException{

  private static final String DEFAULT_MESSAGE = "Failed to retrieve address from the external service.";

  public AddressRetrievalException() {
    super(DEFAULT_MESSAGE);
  }
}
