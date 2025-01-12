package com.compassuol.sp.challenge.msproducts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException{

  private static final String MESSAGE = "Product not found";

    public ProductNotFoundException() {
      super(MESSAGE);
  }
}
