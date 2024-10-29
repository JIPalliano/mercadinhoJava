package com.example.mercadinho.controller.request;

import lombok.Builder;

@Builder
public record LoginRequest(
        String username,
        String password
) {
}
