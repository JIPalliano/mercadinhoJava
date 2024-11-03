package com.example.mercadinho.controller.request;

import lombok.Builder;

@Builder
public record ProductRequest(
        String id,
        String name,
        Float price,
        Long quantity
) {
}
