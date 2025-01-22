package com.example.mercadinho.domain.service.shipping;

import com.example.mercadinho.domain.service.shipping.response.ShippingResponse;
import reactor.core.publisher.Mono;

public interface ShippingFacade {

    Mono<ShippingResponse> calculateShipping(String cep);
}
