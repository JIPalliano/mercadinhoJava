package com.example.mercadinho.controller.request;

import lombok.Builder;

@Builder
public record UserRequest(
        String username,
        String password,
        String role
) {
}
