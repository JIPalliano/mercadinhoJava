package com.example.mercadinho.repository.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;

import java.util.List;

@Builder
public record ShoppingCartEntity(
        @Id
        String id,
        List<ProductEntity> products,
        String date
) {
}
