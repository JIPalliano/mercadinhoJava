package com.example.mercadinho.infrastructure.integration.viacep.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ViaCepResponse(
        @JsonProperty("cep")
        String cep,
        @JsonProperty("logradouro")
        String address,
        @JsonProperty("localidade")
        String city,
        @JsonProperty("bairro")
        String neighborhood,
        @JsonProperty("estado")
        String state
) {
}
