package com.compassuol.sp.challenge.msorders.controller;

import com.compassuol.sp.challenge.msorders.constant.Status;
import com.compassuol.sp.challenge.msorders.dto.CancelOrderRequestDTO;
import com.compassuol.sp.challenge.msorders.dto.RequestOrderDTO;
import com.compassuol.sp.challenge.msorders.model.OrderModel;
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
  public ResponseEntity<List<OrderModel>> getOrdersByStatus(@RequestParam Status status) {
    return ResponseEntity.status(HttpStatus.OK).body(service.getOrdersByStatus(status));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> getOrderById(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> createOrder(@RequestBody @Valid RequestOrderDTO request) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(request));
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody @Valid RequestOrderDTO request) {
    return ResponseEntity.status(HttpStatus.OK).body(service.updateOrder(id, request));
  }

  @PostMapping(value = "/{id}/cancel", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Object> cancelOrder(@PathVariable Long id, @RequestBody CancelOrderRequestDTO cancelOrderRequest) {
    return ResponseEntity.status(HttpStatus.OK).body(service.cancelOrderById(id, cancelOrderRequest));
  }
}
