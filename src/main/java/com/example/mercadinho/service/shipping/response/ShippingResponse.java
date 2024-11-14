package com.example.mercadinho.service.shipping.response;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ShippingResponse(
        BigDecimal calculate
) {
}
