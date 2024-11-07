package com.example.mercadinho.domain.repository.model.entity;

import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;


@Builder

@Document(collection = "productEntity")
public record ProductEntity(
        @Id
        String id,
        String name,
        BigDecimal price,
        Integer quantity
) {
}
