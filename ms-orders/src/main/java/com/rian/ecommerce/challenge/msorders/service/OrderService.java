package com.rian.ecommerce.challenge.msorders.service;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.rian.ecommerce.challenge.msorders.constant.OrderStatus;
import com.rian.ecommerce.challenge.msorders.exception.OrderNotFoundException;
import com.rian.ecommerce.challenge.msorders.model.Order;
import com.rian.ecommerce.challenge.msorders.model.request.CancelOrderRequest;
import com.rian.ecommerce.challenge.msorders.model.request.OrderRequest;
import com.rian.ecommerce.challenge.msorders.model.response.OrderResponse;
import com.rian.ecommerce.challenge.msorders.repository.OrderRepository;
import com.rian.ecommerce.challenge.msorders.utils.OrderUtils;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository repository;
  private final OrderUtils utils;

  public List<Order> getOrdersByStatus(OrderStatus status) {
    return repository.findByStatus(status);
  }

  public OrderResponse findByReference(String reference) {
    var order = repository.findByReference(reference).orElseThrow(OrderNotFoundException::new);
    return utils.mapToResponse(order);
  }

  public OrderResponse createOrder(OrderRequest request) {
    var entity = utils.mapToEntity(request);
    entity.setAddress(utils.mapToAddress(request.address()));
    entity.setSubtotalValue(12.2); //subtotal dinamically introduced with kafka consumer
    entity.setTotalValue(utils.evaluateTotalValue(12.2, request.paymentType())); //subtotal dinamically introduced with kafka consumer
    entity.setDiscount(utils.evaluateDiscount(entity));

    var order = repository.save(entity);
    order.setReference(utils.saveWithReference(order.getId()));
    order.setCreatedAt(LocalDateTime.now()); // guess if is valid make a map to threat it
    order.setUpdatedAt(LocalDateTime.now());
    order.setStatus(OrderStatus.CREATED);

    return utils.mapToResponse(repository.save(order));
  }

  public void cancelOrder(String reference, CancelOrderRequest request) {
    //might return OrderResponse
  }

  public void updateOrder(Long id, OrderRequest request) {
    //might return OrderResponse
  }
}
