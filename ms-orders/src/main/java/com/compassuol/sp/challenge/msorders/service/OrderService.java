package com.compassuol.sp.challenge.msorders.service;

import com.compassuol.sp.challenge.msorders.constant.OrderStatus;
import com.compassuol.sp.challenge.msorders.exception.OrderNotFoundException;
import com.compassuol.sp.challenge.msorders.mapper.OrderMapper;
import com.compassuol.sp.challenge.msorders.model.Order;
import com.compassuol.sp.challenge.msorders.model.request.CancelOrderRequest;
import com.compassuol.sp.challenge.msorders.model.request.OrderRequest;
import com.compassuol.sp.challenge.msorders.model.response.OrderResponse;
import com.compassuol.sp.challenge.msorders.repository.OrderRepository;
import com.compassuol.sp.challenge.msorders.utils.OrderUtils;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository repository;
  private final OrderMapper mapper;
  private final OrderUtils utils;

  public List<Order> getOrdersByStatus(OrderStatus status) {
    return repository.findByStatus(status);
  }

  public OrderResponse findById(String id) {
    var order = repository.findById(id).orElseThrow(OrderNotFoundException::new);
    return mapper.toOrderResponse(order);
  }

  public void createOrder(OrderRequest request) {


  }

  public void cancelOrder(Long id, CancelOrderRequest request) {
    //might return OrderResponse
  }

  public void updateOrder(Long id, OrderRequest request) {
    //might return OrderResponse
  }
}
