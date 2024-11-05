package com.example.mercadinho.controller.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(
        String id,
        String name,
        BigDecimal price,
        Integer quantity
) {
}
