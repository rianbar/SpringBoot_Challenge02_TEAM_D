package com.compassuol.sp.challenge.msorders.controller;

import com.compassuol.sp.challenge.msorders.constant.OrderStatus;
import com.compassuol.sp.challenge.msorders.model.Order;
import com.compassuol.sp.challenge.msorders.model.request.CancelOrderRequest;
import com.compassuol.sp.challenge.msorders.model.request.CreateOrderRequest;
import com.compassuol.sp.challenge.msorders.model.response.OrderResponse;
import com.compassuol.sp.challenge.msorders.service.OrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService service;

  @GetMapping
  public ResponseEntity<List<Order>> getOrdersByStatus(@RequestParam OrderStatus status) {
    return ResponseEntity.status(HttpStatus.OK).body(service.getOrdersByStatus(status));
  }

  /*
  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) {
    return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(request));
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> updateOrder(@PathVariable Long id, @RequestBody CreateOrderRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(service.updateOrder(id, request));
  }

  @PostMapping(value = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long id, @RequestBody CancelOrderRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(service.cancelOrder(id, request));
  }
    */
}
