package com.example.mercadinho.controller.request;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PaymentRequest(
        String payId,
        String status,
        Integer quantity,
        BigDecimal total
) {
}
