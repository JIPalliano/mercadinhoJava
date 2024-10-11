package com.example.mercadinho.repository.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Document(collection = "product")
public record ProductEntity(
        @Id
        String id,
        String name,
        Float price
) {
}
