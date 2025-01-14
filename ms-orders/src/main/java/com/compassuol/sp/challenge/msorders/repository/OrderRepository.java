package com.compassuol.sp.challenge.msorders.repository;

import com.compassuol.sp.challenge.msorders.constant.OrderStatus;
import com.compassuol.sp.challenge.msorders.model.Order;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
  List<Order> findByStatus(OrderStatus status);
}
