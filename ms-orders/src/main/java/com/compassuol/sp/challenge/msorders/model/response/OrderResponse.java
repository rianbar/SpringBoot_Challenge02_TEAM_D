package com.compassuol.sp.challenge.msorders.model.response;

import java.util.List;

import com.compassuol.sp.challenge.msorders.constant.PaymentType;
import com.compassuol.sp.challenge.msorders.constant.OrderStatus;
import com.compassuol.sp.challenge.msorders.model.Address;
import com.compassuol.sp.challenge.msorders.model.OrderProducts;

public record OrderResponse(
  String id,
  List<OrderProducts> products,
  Address address,
  PaymentType paymentType,
  Double subtotalValue,
  Double discount,
  Double totalValue,
  String creationDate,
  OrderStatus status,
  String cancelReason,
  String cancelDate
) {
}
