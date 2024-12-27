package com.example.mercadinho.controller.response;

import com.example.mercadinho.infrastructure.repository.model.Product;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record ShoppingCartResponse (
        String id,
        List<Product>products,
        BigDecimal totalPrice,
        Integer quantity,
        String userId,
        String date
) {
}
