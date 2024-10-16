package com.example.mercadinho.repository.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document(collection = "shoppingCartEntity")
public record ShoppingCartEntity(
        @Id
        String id,
        List<ProductEntity> products,
        String date
) {
}
