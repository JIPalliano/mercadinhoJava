package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.request.ShoppingCartRequest;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;

import java.util.List;

public interface ShoppingCartFacade {
    ShoppingCartEntity create(String idProduct, Integer quantity);

    ShoppingCartEntity find();

    ShoppingCartEntity addProduct(String idProduct, Integer quantity);

    void delete(String idShoppingCart);

    List<ShoppingCartEntity> findAll();

//    ShoppingCartEntity findShoppingCartCookie();

}
