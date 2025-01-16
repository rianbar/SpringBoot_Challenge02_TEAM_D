package com.rian.ecommerce.challenge.msorders.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.rian.ecommerce.challenge.msorders.constant.OrderStatus;
import com.rian.ecommerce.challenge.msorders.model.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
  List<Order> findByStatus(OrderStatus status);
  Optional<Order> findByReference(String reference);
}
