package com.compassuol.sp.challenge.msorders.controller;

import com.compassuol.sp.challenge.msorders.constant.StatusOrderEnum;
import com.compassuol.sp.challenge.msorders.dto.CancelOrderRequestDTO;
import com.compassuol.sp.challenge.msorders.dto.RequestOrderDTO;
import com.compassuol.sp.challenge.msorders.model.OrderModel;
import com.compassuol.sp.challenge.msorders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderModel>> getOrdersByStatusAndSort(
            @RequestParam(name = "status", required = false) StatusOrderEnum status) {
        return ResponseEntity.ok(orderService.getOrdersByStatusSortedByDate(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOrderById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(orderService.findByIdService(id));
    }

    @PostMapping
    public ResponseEntity<Object> createOrder(@RequestBody @Valid RequestOrderDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrderService(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateOrder(@PathVariable Long id, @RequestBody @Valid RequestOrderDTO request) {
        return ResponseEntity.ok(orderService.updateOrderService(id, request));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<Object> cancelOrderById(@PathVariable Long id, @RequestBody CancelOrderRequestDTO cancelOrderRequest) {
        return ResponseEntity.ok(orderService.cancelOrderByIdService(id, cancelOrderRequest));
    }
}
