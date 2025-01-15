package com.compassuol.sp.challenge.msorders.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.compassuol.sp.challenge.msorders.model.Order;
import com.compassuol.sp.challenge.msorders.model.request.OrderRequest;
import com.compassuol.sp.challenge.msorders.model.response.OrderResponse;

@Mapper(componentModel = "spring")
public interface OrderMapper {
  @Mapping(target = ".", source = "products")
  @Mapping(target = ".", source = "address")
  OrderResponse toOrderResponse(Order order);

  Order toEntity(OrderRequest request); // find a way to ignore unmapping fields
}
