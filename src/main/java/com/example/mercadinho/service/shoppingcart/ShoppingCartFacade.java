package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.request.ShoppingCartRequest;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;

import java.util.List;

public interface ShoppingCartFacade {
    ShoppingCartEntity createShoppingCart(String idProduct, ShoppingCartRequest request);

    ShoppingCartEntity findShoppingCartByUser();

    ShoppingCartEntity findShoppingCartAdd(String idProduct);

    void deleteShoppingCart(String idShoppingCart);

    List<ShoppingCartEntity> findAll();

    ShoppingCartEntity findShoppingCartCookie();

}
