package com.compassuol.sp.challenge.msorders.model;

public record Address(
  String street,
  Integer number,
  String complement,
  String city,
  String state,
  String zipCode
) {
}
