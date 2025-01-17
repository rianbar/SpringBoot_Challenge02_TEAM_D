package com.rian.ecommerce.challenge.msorders.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.rian.ecommerce.challenge.msorders.constant.OrderStatus;
import com.rian.ecommerce.challenge.msorders.model.Order;
import com.rian.ecommerce.challenge.msorders.model.request.CancelOrderRequest;
import com.rian.ecommerce.challenge.msorders.model.request.OrderRequest;
import com.rian.ecommerce.challenge.msorders.model.response.OrderResponse;
import com.rian.ecommerce.challenge.msorders.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

  private final OrderService service;

  @GetMapping(value = "status/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable OrderStatus status) {
    return ResponseEntity.status(HttpStatus.OK).body(service.getOrdersByStatus(status));
  }

  @GetMapping(value = "/{reference}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> getOrderByReference(@PathVariable String reference) {
    return ResponseEntity.status(HttpStatus.OK).body(service.findByReference(reference));
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(request));
  }

  @PutMapping(value = "/{reference}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> updateOrder(@PathVariable String reference, @RequestBody OrderRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(service.updateOrder(reference, request));
  }

  @PostMapping(value = "/{reference}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<OrderResponse> cancelOrder(@PathVariable String reference, @RequestBody CancelOrderRequest request) {
    return ResponseEntity.status(HttpStatus.OK).body(service.cancelOrder(reference, request));
  }
}
