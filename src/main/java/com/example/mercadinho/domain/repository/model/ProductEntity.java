package com.example.mercadinho.domain.repository.model;

import lombok.Builder;
import lombok.Data;
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
