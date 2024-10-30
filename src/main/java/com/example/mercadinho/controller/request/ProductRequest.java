package com.example.mercadinho.controller.request;

import lombok.Builder;

@Builder
public record ProductRequest(
        String name,
        Float price
) {
}
