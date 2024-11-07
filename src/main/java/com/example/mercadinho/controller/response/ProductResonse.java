package com.example.mercadinho.controller.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResonse(
        String name,
        BigDecimal price
) {
}
