package com.example.mercadinho.controller.request;

import jakarta.validation.constraints.Email;
import lombok.Builder;

@Builder
public record EmailRequest(
        String username,
        @Email(regexp = ".+[@].+[\\.].+")
        String email
) {
}
