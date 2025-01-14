package com.compassuol.sp.challenge.msorders.model;

import com.compassuol.sp.challenge.msorders.constant.PaymentType;
import com.compassuol.sp.challenge.msorders.constant.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
public record Order(
  @MongoId
  String id,
  List<OrderProducts> products,
  Address address,
  PaymentType paymentType,
  Double subtotalValue,
  Double discount,
  Double totalValue,
  LocalDateTime creationDate,
  OrderStatus status,
  String cancelReason,
  LocalDateTime cancelDate
) {
}
