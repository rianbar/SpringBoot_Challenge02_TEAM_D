package com.compassuol.sp.challenge.msorders.model.request;

public record AddressRequest(
  String street,
  Integer number,
  String zipCode
) {
}
