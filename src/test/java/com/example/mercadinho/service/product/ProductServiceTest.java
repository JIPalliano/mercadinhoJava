package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {


    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test creating the product")
    void addProduct_shouldSaveAndReturnProduct() {
        ProductRequest request = ProductRequest.builder()
                .name("Product A")
                .price(BigDecimal.valueOf(12.0))
                .quantity(100)
                .build();

        ProductEntity product  = ProductEntity.builder()
                .name("Product A")
                .price(BigDecimal.valueOf(12.0))
                .quantity(100)
                .build();
        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);

        ProductEntity result = productService.createProduct(request);

        assertNotNull(result);
        assertEquals("Product A", result.name());
        assertEquals(100, result.quantity());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Testing find product exist")
    void findProductById_shouldReturnProduct_whenProductExists() {

        ProductEntity product = ProductEntity.builder()
                .id("user123")
                .name("Product A")
                .price(BigDecimal.valueOf(12.0))
                .quantity(1)
                .build();
        when(productRepository.findById(product.id())).thenReturn(Optional.of(product));


        Optional<ProductEntity> result = Optional.ofNullable(productService.findById(product));


        assertTrue(result.isPresent());
        assertEquals("Product A", result.get().name());
        verify(productRepository, times(1)).findById(product.id());
    }

    @Test
    @DisplayName("Testing message in exception, when product does not exist")
    void findProductById_shouldThrowException_whenProductDoesNotExist() {

        when(productRepository.findById("user123")).thenReturn(Optional.empty());


        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                productService.updateProduct("user123", ProductRequest.builder()
                .id("user123")
                .name("Product B")
                .price(BigDecimal.valueOf(15.0))
                .quantity(2)
                .build()));
        assertEquals("No value present", exception.getMessage());

        verify(productRepository, times(1)).findById("user123");
    }

    @Test
    @DisplayName("Test update the product")
    void updateProduct_shouldUpdateProduct() {

        ProductEntity product = ProductEntity.builder()
                .id("user123")
                .name("Product A")
                .price(BigDecimal.valueOf(12.0))
                .quantity(1)
                .build();
        ProductEntity updateEntity = ProductEntity.builder()
                .id("user123")
                .name("Product A")
                .price(BigDecimal.valueOf(12.0))
                .quantity(150)
                .build();
        when(productRepository.findById("user123")).thenReturn(Optional.of(product));
        when(productRepository.save(any(ProductEntity.class))).thenReturn(updateEntity);


        ProductEntity updatedProduct = productService.updateProduct("user123", ProductRequest.builder()
                .id("user123")
                .name("Product B")
                .price(BigDecimal.valueOf(15.0))
                .quantity(150)
                .build());

//        ArgumentCaptor<ProductEntity> captor = ArgumentCaptor.forClass(ProductEntity.class);
//        verify(productRepository).save(captor.capture());
//
//        ProductEntity expectedProduct = captor.getValue();


        assertNotNull(updatedProduct);
        assertEquals("user123", updatedProduct.id());
        assertEquals("Product A", updatedProduct.name());
        assertEquals(150, updatedProduct.quantity());
        verify(productRepository, times(1)).findById("user123");
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }
}