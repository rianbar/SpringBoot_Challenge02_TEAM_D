package com.compassuol.sp.challenge.msproducts.controller;

import com.compassuol.sp.challenge.msproducts.dto.RequestProductDTO;
import com.compassuol.sp.challenge.msproducts.model.ProductModel;
import com.compassuol.sp.challenge.msproducts.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductModel>> getProducts() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
    }

    @PostMapping
    public ResponseEntity<Object> createProduct(@RequestBody @Valid RequestProductDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProductService(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable("id") long id, @RequestBody @Valid RequestProductDTO dto) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.updateProductService(dto, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductById(@PathVariable("id") long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findProductByIdService(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("id") long id) {
        productService.deleteProductById(id);
        return ResponseEntity.noContent().build();
    }
}