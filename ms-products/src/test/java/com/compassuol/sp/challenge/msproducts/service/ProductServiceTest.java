package com.compassuol.sp.challenge.msproducts.service;

import com.compassuol.sp.challenge.msproducts.dto.RequestProductDTO;
import com.compassuol.sp.challenge.msproducts.dto.ResponseProductDTO;
import com.compassuol.sp.challenge.msproducts.exception.type.BusinessErrorException;
import com.compassuol.sp.challenge.msproducts.exception.type.ProductNotFoundException;
import com.compassuol.sp.challenge.msproducts.model.ProductModel;
import com.compassuol.sp.challenge.msproducts.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {
    ProductModel model;
    RequestProductDTO request;
    ResponseProductDTO response;

    @InjectMocks
    ProductService service;

    @Mock
    ProductRepository repository;

    @Mock
    TransferObjectService transferObject;

    @BeforeEach
    void setUp() {
        model = new ProductModel(1L, "product", "this is my mock description", 12.50);
        request = new RequestProductDTO("product", "this is my mock description", 12.50);
        response = new ResponseProductDTO(1L,"product", "this is my mock description", 12.50);
    }

    @Test
    void updateProduct_withValidData_returnObject() {
        var productModelCaptor = ArgumentCaptor.forClass(ProductModel.class);
        when(repository.findById(anyLong())).thenReturn(Optional.of(model));
        when(transferObject.toDTO(productModelCaptor.capture())).thenReturn(response);

        var response = service.updateProductService(request, 1L);

        assertNotNull(response);
    }

    @Test
    void updateProduct_withInvalidData_ThrowException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.updateProductService(request, 1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void findProductById_withValidData_returnObject() {
        var productModelCaptor = ArgumentCaptor.forClass(ProductModel.class);
        when(repository.findById(anyLong())).thenReturn(Optional.of(model));
        when(transferObject.toDTO(productModelCaptor.capture())).thenReturn(response);

        var response = service.findProductByIdService(1L);

        assertNotNull(response);
    }

    @Test
    void findProductById_withinValidData_throwException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.findProductByIdService(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void deleteProduct_withInvalidData_throwException() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.deleteProductById(1L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    void createProduct_withValidData_returnObject() {
        var productModelCaptor = ArgumentCaptor.forClass(ProductModel.class);
        when(repository.findByName(anyString())).thenReturn(Optional.empty());
        when(repository.save(productModelCaptor.capture())).thenReturn(model);
        when(transferObject.toDTO(productModelCaptor.capture())).thenReturn(response);

        var response = service.createProductService(request);

        assertNotNull(response);
    }

    @Test
    void createProduct_withInvalidData_throwException() {
        when(repository.findByName(anyString())).thenReturn(Optional.of(model));

        assertThatThrownBy(() ->  service.createProductService(request))
                .isInstanceOf(BusinessErrorException.class);
    }
}
