package com.compassuol.sp.challenge.msproducts.repository;

import com.compassuol.sp.challenge.msproducts.model.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductModel, Long> {
    Optional<ProductModel> findByName(String name);
}
