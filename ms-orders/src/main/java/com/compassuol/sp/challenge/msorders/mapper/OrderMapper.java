package com.compassuol.sp.challenge.msorders.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.compassuol.sp.challenge.msorders.model.Order;
import com.compassuol.sp.challenge.msorders.model.response.OrderResponse;

@Mapper(componentModel = "spring")
public interface OrderMapper {  // remember to fix mapper toOrderResponse method error
  @Mapping(target = "cancelReason", ignore = true)
  @Mapping(target = "cancelDate", ignore = true)
  @Mapping(target = ".", source = "products")
  @Mapping(target = ".", source = "address")
  OrderResponse toOrderResponse(Order order);
}
