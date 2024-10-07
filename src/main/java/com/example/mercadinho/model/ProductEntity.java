package com.example.mercadinho.model;

import lombok.Builder;
import org.springframework.data.annotation.Id;

@Builder
public record ProductEntity(
        @Id
        String id,
        String name,
        Float price
) {
}
