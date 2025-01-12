package com.compassuol.sp.challenge.msproducts.controller;

import com.compassuol.sp.challenge.msproducts.model.Product;
import com.compassuol.sp.challenge.msproducts.model.request.ProductRequest;
import com.compassuol.sp.challenge.msproducts.model.response.ProductResponse;
import com.compassuol.sp.challenge.msproducts.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService service;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<List<Product>> getProducts() {
    return ResponseEntity.status(HttpStatus.OK).body(service.getAllProducts());
  }

  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest dto) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.createProductService(dto));
  }

  @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable long id, @RequestBody @Valid ProductRequest dto) {
    return ResponseEntity.status(HttpStatus.OK).body(service.updateProductService(dto, id));
  }

  @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ProductResponse> getProductById(@PathVariable long id) {
    return ResponseEntity.status(HttpStatus.OK).body(service.findProductByIdService(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<ProductResponse> deleteProduct(@PathVariable long id) {
    service.deleteProductById(id);
    return ResponseEntity.noContent().build();
  }
}
