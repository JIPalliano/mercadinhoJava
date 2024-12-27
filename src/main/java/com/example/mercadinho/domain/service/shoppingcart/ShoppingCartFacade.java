package com.example.mercadinho.domain.service.shoppingcart;

import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.infrastructure.repository.model.entity.ShoppingCartEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ShoppingCartFacade {
    Mono<ShoppingCartEntity> create(String idProduct, Integer quantity);

    Mono<ShoppingCartResponse> find();

    Mono<ShoppingCartEntity> addProduct(String idProduct, Integer quantity);

    Mono<Void> delete();

    Flux<ShoppingCartEntity> findAll();

//    ShoppingCartEntity findShoppingCartCookie();

}
