package com.compassuol.sp.challenge.msproducts.model.response;

import lombok.Builder;

@Builder
public record ProductResponse(
  Long id,
  String name,
  String description,
  Double price) {
}
