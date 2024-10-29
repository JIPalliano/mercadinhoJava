package com.example.mercadinho.controller.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        String username,
        String role,
        String token
) {
}
