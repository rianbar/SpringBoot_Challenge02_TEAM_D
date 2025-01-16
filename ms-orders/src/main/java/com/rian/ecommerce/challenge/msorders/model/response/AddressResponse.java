package com.rian.ecommerce.challenge.msorders.model.response;

public record AddressResponse(
  String cep,
  String logradouro,
  String complemento,
  String bairro,
  String localidade,
  String uf
) {}
