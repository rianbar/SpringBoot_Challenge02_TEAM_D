package com.rian.ecommerce.challenge.msorders.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Order not found";

  public OrderNotFoundException() {
    super(DEFAULT_MESSAGE);
  }
}
