package com.compassuol.sp.challenge.msproducts.service;

import com.compassuol.sp.challenge.msproducts.exception.ProductNotFoundException;
import com.compassuol.sp.challenge.msproducts.mapper.ProductMapper;
import com.compassuol.sp.challenge.msproducts.exception.BusinessRuleException;
import com.compassuol.sp.challenge.msproducts.model.Product;
import com.compassuol.sp.challenge.msproducts.model.request.ProductRequest;
import com.compassuol.sp.challenge.msproducts.model.response.ProductResponse;
import com.compassuol.sp.challenge.msproducts.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository repository;
  private final ProductMapper mapper;

  public List<Product> getAllProducts() {
    return repository.findAll();
  }

  public ProductResponse createProductService(ProductRequest request) {
    var product = repository.findByName(request.name());
    if (product.isPresent()) throw new BusinessRuleException();
    var newProduct = repository.save(mapper.toProduct(request));
    return mapper.toResponse(newProduct);
  }

  //find a way to mapper the product introducing the id
  public ProductResponse updateProductService(ProductRequest request, long id) {
    if (repository.findById(id).isEmpty()) throw new ProductNotFoundException();
    var builder = Product.builder().id(id).name(request.name()).description(request.description()).price(request.price()).build();
    var product = repository.save(builder);
    return mapper.toResponse(product);
  }

  public ProductResponse findProductByIdService(long id) {
    Optional<Product> product = repository.findById(id);
    if (product.isEmpty()) throw new ProductNotFoundException();
    return mapper.toResponse(product.get());
  }

  public void deleteProductById(long id) {
    var product = repository.findById(id);
    if (product.isEmpty()) throw new ProductNotFoundException();
    repository.delete(product.get());
  }
}
