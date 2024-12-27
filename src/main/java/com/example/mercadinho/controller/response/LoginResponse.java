package com.example.mercadinho.controller.response;

import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Builder
public record LoginResponse(
        String username,
        List<? extends GrantedAuthority> roles,
        String token
) {
}
