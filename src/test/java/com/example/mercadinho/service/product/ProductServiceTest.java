package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
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
    void addProduct_shouldSaveAndReturnProduct() {
        // Arrange
        ProductRequest request = ProductRequest.builder()
                .id("user123")
                .name("Product A")
                .price(BigDecimal.valueOf(12.0))
                .quantity(1)
                .build();

        ProductEntity expectedProduct  = ProductEntity.builder()
                .id("user123")
                .name("Product A")
                .price(BigDecimal.valueOf(12.0))
                .quantity(100)
                .build();
        when(productRepository.save(any(ProductEntity.class))).thenReturn(expectedProduct);

        // Act
        ProductEntity result = productService.createProduct(request);

        // Assert
        assertNotNull(result);
        assertEquals("Product A", result.name());
        assertEquals(100, result.quantity());
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }

    @Test
    void findProductById_shouldReturnProduct_whenProductExists() {
        // Arrange
        ProductEntity product = ProductEntity.builder()
                .id("user123")
                .name("Product A")
                .price(BigDecimal.valueOf(12.0))
                .quantity(1)
                .build();
        when(productRepository.findById(product.id())).thenReturn(Optional.of(product));

        // Act
        Optional<ProductEntity> result = Optional.ofNullable(productService.findById(product));

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Product A", result.get().name());
        verify(productRepository, times(1)).findById(product.id());
    }

    @Test
    void findProductById_shouldThrowException_whenProductDoesNotExist() {
        // Arrange
        when(productRepository.findById("user123")).thenReturn(Optional.empty());

        // Act & Assert
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
    void updateQuantity_shouldUpdateProductQuantity() {
        // Arrange
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

        // Act
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

        // Assert
        assertNotNull(updatedProduct);
        assertEquals("user123", updatedProduct.id());
        assertEquals("Product A", updatedProduct.name());
        assertEquals(150, updatedProduct.quantity());
        verify(productRepository, times(1)).findById("user123");
        verify(productRepository, times(1)).save(any(ProductEntity.class));
    }
}