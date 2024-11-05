package com.example.mercadinho.controller.response;

import com.example.mercadinho.domain.repository.model.ProductEntity;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResonse(
        String name,
        BigDecimal price
) {
    public ProductEntity fromEntity(ProductEntity productEntity) {
        return ProductEntity.builder()
                .name(name)
                .price(price)
                .build();
    }
}
