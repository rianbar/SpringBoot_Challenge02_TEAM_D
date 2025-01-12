package com.compassuol.sp.challenge.msproducts.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

@Validated
public record ProductRequest(
  @NotEmpty(message = "field 'name' is mandatory")
  String name,
  @NotNull(message = "field 'description' cannot be null")
  @Size(min = 10, message = "field 'description' must have at least 10 characters")
  String description,
  @NotNull(message = "field 'value' cannot be null")
  @Min(value = 0L, message = "field 'price' cannot be negative number")
  Double price
) {
}
