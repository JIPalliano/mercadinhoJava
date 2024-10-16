package com.example.mercadinho.service;

import com.example.mercadinho.repository.model.ProductEntity;
import com.example.mercadinho.repository.model.ShoppingCartEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ShoppingCartFacade {
    ShoppingCartEntity createShoppingCart(String id, ShoppingCartEntity request);

    ShoppingCartEntity findShoppingCartAdd(String idProduct, String idShoppingCart, HttpServletResponse response, ProductEntity productResponse);

    List<ShoppingCartEntity> findAll();

    ShoppingCartEntity findShoppingCartCookie();

}
