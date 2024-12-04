package com.example.mercadinho.controller.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "Detalhes do erro retornado pela API.")
@Builder
public record ErrorResponse(
        int errorCode,
        String message,
        String timeStamp
) {
}
