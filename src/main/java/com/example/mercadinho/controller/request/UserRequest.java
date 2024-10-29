package com.example.mercadinho.controller.request;


public record UserRequest(
        String username,
        String password,
        String role
) {
}
