package com.rian.ecommerce.challenge.msorders.model.request;

public record AddressRequest(
  String street,
  Integer number,
  String zipCode
) {
}
