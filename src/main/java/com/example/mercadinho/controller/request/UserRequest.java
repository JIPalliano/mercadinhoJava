package com.example.mercadinho.controller.request;

import lombok.Builder;

import java.util.List;

@Builder
public record UserRequest(
        String username,
        String password,
        List<String> roles
) {
}
