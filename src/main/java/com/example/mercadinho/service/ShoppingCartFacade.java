package com.example.mercadinho.service;

import com.example.mercadinho.repository.model.ProductEntity;
import com.example.mercadinho.repository.model.ShoppingCartEntity;
import java.util.List;

public interface ShoppingCartFacade {
    ShoppingCartEntity createShoppingCart(String id, ShoppingCartEntity request);

    ShoppingCartEntity findShoppingCartAdd(String idProduct, String idShoppingCart, ShoppingCartEntity response, ProductEntity productResponse);

    List<ShoppingCartEntity> findAll();

}
