package com.example.mercadinho.domain.service.cookies.response;

import lombok.Builder;

@Builder
public record CookieResponse(
        String value,
        Boolean wrap,
        String domain,
        String path,
        Long maxAge,
        Boolean isSecure,
        Boolean isHttpOnly
) {
}
