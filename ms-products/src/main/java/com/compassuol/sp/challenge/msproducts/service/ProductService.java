package com.compassuol.sp.challenge.msproducts.service;

import com.compassuol.sp.challenge.msproducts.dto.RequestProductDTO;
import com.compassuol.sp.challenge.msproducts.dto.ResponseProductDTO;
import com.compassuol.sp.challenge.msproducts.exception.type.BusinessErrorException;
import com.compassuol.sp.challenge.msproducts.exception.type.ProductNotFoundException;
import com.compassuol.sp.challenge.msproducts.model.ProductModel;
import com.compassuol.sp.challenge.msproducts.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final TransferObjectService transferObject;

    public List<ProductModel> getAllProducts() {
        return productRepository.findAll();
    }

    public ResponseProductDTO updateProductService(RequestProductDTO dto, long id) {
        if (productRepository.findById(id).isEmpty()) throw new ProductNotFoundException("Product not found");
        var model = productRepository.save(transferObject.toModelUpdated(id, dto));
        return transferObject.toDTO(model);
    }

    public ResponseProductDTO findProductByIdService(long id) {
        Optional<ProductModel> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ProductNotFoundException("Product Not Found");
        return transferObject.toDTO(product.get());
    }

    public void deleteProductById(long id) {
        var findProduct = productRepository.findById(id);
        if (findProduct.isEmpty()) throw new ProductNotFoundException("Product not found " + id);
        productRepository.delete(findProduct.get());
    }

    public ResponseProductDTO createProductService(RequestProductDTO dto) {
        if (productRepository.findByName(dto.getName()).isPresent())
            throw new BusinessErrorException("Product name already exists");
        var newProduct = productRepository.save(transferObject.toModel(dto));
        return transferObject.toDTO(newProduct);
    }
}
