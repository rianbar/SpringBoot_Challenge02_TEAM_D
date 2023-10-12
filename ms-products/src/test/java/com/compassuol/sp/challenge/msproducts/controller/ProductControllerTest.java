package com.compassuol.sp.challenge.msproducts.controller;

import com.compassuol.sp.challenge.msproducts.model.ProductModel;
import com.compassuol.sp.challenge.msproducts.repository.ProductRepository;
import com.compassuol.sp.challenge.msproducts.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @MockBean
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        // Limpar a tabela de produtos antes de cada teste
        Mockito.when(productRepository.findAll()).thenReturn(
                Arrays.asList(
                        new ProductModel("Product 1", "Product 1 description", 10.0),
                        new ProductModel("Product 2", "Product 2 description", 20.0)
                )
        );
    }

    @Test
    public void testGetProducts() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].value").value(10.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Product 2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].value").value(20.0));
    }

    @Test
    public void testGetProductsEmpty() throws Exception {
        // Limpar a tabela de produtos para simular uma lista vazia
        Mockito.when(productRepository.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders.get("/products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetProductByIdSuccess() throws Exception {
        Long productId = 1L;
        ProductModel product = new ProductModel("Product 1", "Product 1 description", 10.0);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Product 1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.value").value(10.0));
    }

    @Test
    public void testGetProductById_ProductNotFound() throws Exception {
        Long productId = 999L; // Use um ID que não corresponda a um produto existente no seu banco de dados simulado
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
