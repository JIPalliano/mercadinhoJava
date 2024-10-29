package com.example.mercadinho.domain.repository.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Builder
@Document(collection = "shopping-cart")
public record ShoppingCartEntity(
        @Id
        String id,
        @Indexed(unique = true)
        String userId,
        List<ProductEntity> products,
        String date
) {
}
