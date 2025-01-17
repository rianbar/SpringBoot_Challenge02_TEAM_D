package com.rian.ecommerce.challenge.msorders.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.rian.ecommerce.challenge.msorders.model.Address;
import com.rian.ecommerce.challenge.msorders.model.Order;
import com.rian.ecommerce.challenge.msorders.model.request.OrderRequest;
import com.rian.ecommerce.challenge.msorders.model.response.AddressResponse;
import com.rian.ecommerce.challenge.msorders.model.response.OrderResponse;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderMapper {
  @Mapping(target = ".", source = "products")
  @Mapping(target = ".", source = "address")
  OrderResponse toOrderResponse(Order order);

  @Mapping(target = "address", source = "address")
  Order toEntity(OrderRequest request, Address address);

  @Mapping(target = "street", source = "response.logradouro")
  @Mapping(target = "number", source = "number")
  @Mapping(target = "zipCode", source = "response.cep")
  @Mapping(target = "complement", source = "response.complemento")
  @Mapping(target = "city", source = "response.localidade")
  @Mapping(target = "state", source = "response.uf")
  Address toAddress(AddressResponse response, Integer number);
}
