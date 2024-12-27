package com.example.mercadinho.controller.response;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(
        String id,
        String username,
        List<String> roles
) {
}
