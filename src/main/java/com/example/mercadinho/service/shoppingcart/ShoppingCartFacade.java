package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;

import java.util.List;

public interface ShoppingCartFacade {
    ShoppingCartEntity create(String idProduct, Integer quantity);

    ShoppingCartResponse find();

    ShoppingCartEntity addProduct(String idProduct, Integer quantity);

    void delete();

    List<ShoppingCartEntity> findAll();

//    ShoppingCartEntity findShoppingCartCookie();

}
