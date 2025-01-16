package com.rian.ecommerce.challenge.msorders.utils;


import org.springframework.stereotype.Component;

import com.rian.ecommerce.challenge.msorders.constant.PaymentType;
import com.rian.ecommerce.challenge.msorders.exception.AddressRetrievalException;
import com.rian.ecommerce.challenge.msorders.mapper.OrderMapper;
import com.rian.ecommerce.challenge.msorders.model.Address;
import com.rian.ecommerce.challenge.msorders.model.Order;
import com.rian.ecommerce.challenge.msorders.model.request.AddressRequest;
import com.rian.ecommerce.challenge.msorders.model.request.OrderRequest;
import com.rian.ecommerce.challenge.msorders.model.response.OrderResponse;
import com.rian.ecommerce.challenge.msorders.proxy.AddressProviderClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OrderUtils {

  private final OrderMapper mapper;
  private final AddressProviderClient provider;

  public OrderResponse mapToResponse(Order order) {
    return mapper.toOrderResponse(order);
  }

  public Order mapToEntity(OrderRequest request) {
    return mapper.toEntity(request);
  }

  public Address mapToAddress(AddressRequest request) {
    try {
      var response = provider.getAdderss(request.zipCode());
      return mapper.toAddress(response, request.number());
    } catch (RuntimeException ex) { throw new AddressRetrievalException(); }
  }

  public String saveWithReference(String id) {
    String reference = id.substring(id.length() - 5, id.length());
    return reference;
  }

  public Double evaluateTotalValue(Double subtotal, PaymentType paymentType) {
    if (paymentType.equals(PaymentType.PIX)) return (subtotal * 0.5) / 100;
    return subtotal;
  }

  public Double evaluateDiscount(Order order) {
    return order.getPaymentType().equals(PaymentType.PIX) ? 0.5 : 0.0;
  }
}
