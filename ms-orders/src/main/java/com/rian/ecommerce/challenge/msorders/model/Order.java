package com.rian.ecommerce.challenge.msorders.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.rian.ecommerce.challenge.msorders.constant.OrderStatus;
import com.rian.ecommerce.challenge.msorders.constant.PaymentType;

@Data
@Builder
@Document
public class Order {
  @MongoId
  private String id;
  private List<OrderProducts> products;
  private Address address;
  private PaymentType paymentType;
  private Double subtotalValue;
  private Double discount;
  private Double totalValue;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private OrderStatus status;
  private String reference;
  private String cancelReason;
  private LocalDateTime cancelDate;
}
