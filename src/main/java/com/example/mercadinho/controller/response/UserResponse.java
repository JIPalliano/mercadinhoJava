package com.example.mercadinho.controller.response;

import lombok.Builder;

@Builder
public record UserResponse(
        String id,
        String username,
        String role
) {
}
