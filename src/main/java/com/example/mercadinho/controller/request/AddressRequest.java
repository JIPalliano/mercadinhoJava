package com.example.mercadinho.controller.request;

import lombok.Builder;

@Builder
public record AddressRequest(
        String address
) {
}
