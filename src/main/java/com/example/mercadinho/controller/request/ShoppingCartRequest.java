package com.example.mercadinho.controller.request;

import com.example.mercadinho.infrastructure.repository.model.entity.ProductEntity;
import lombok.Builder;

import java.util.List;

@Builder
public record ShoppingCartRequest(
        String userId,
        List<ProductEntity> products,
        String date
) {
}