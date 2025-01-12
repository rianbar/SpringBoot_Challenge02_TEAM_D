package com.compassuol.sp.challenge.msproducts.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.compassuol.sp.challenge.msproducts.model.Product;
import com.compassuol.sp.challenge.msproducts.model.request.ProductRequest;
import com.compassuol.sp.challenge.msproducts.model.response.ProductResponse;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  @Mapping(target = "id", ignore = true)
  Product toProduct(ProductRequest request);
  ProductResponse toResponse(Product product);
}
