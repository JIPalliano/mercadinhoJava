package com.example.mercadinho.domain.repository.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder

@Document(collection = "productEntity")
public record ProductEntity(
        @Id
        String id,
        String name,
        Float price
) {
}
