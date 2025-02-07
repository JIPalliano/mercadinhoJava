package com.example.mercadinho.controller.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;

import java.util.List;

@Builder
public record UserRequest(
        String username,
        @Email(regexp = ".+[@].+[\\.].+")
        String email,
        String password,
        List<String> roles
) {
}
