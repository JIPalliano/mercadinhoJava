package com.example.mercadinho.controller.response;

import lombok.Builder;

@Builder
public record AddressResponse(
        String address
) {
}
