package com.example.mercadinho.controller.response;

import com.example.mercadinho.domain.repository.model.ProductEntity;
import lombok.Builder;

@Builder
public record ProductResonse(
        String name,
        Float price
) {
    public ProductEntity fromEntity(ProductEntity productEntity) {
        return ProductEntity.builder()
                .name(name)
                .price(price)
                .build();
    }
}
