package com.example.mercadinho.service.shoppingcart;

import com.example.mercadinho.controller.request.ShoppingCartRequest;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ShoppingCartFacade {
    ShoppingCartEntity createShoppingCart(String idProduct, ShoppingCartRequest request);

    ShoppingCartEntity findShoppingCartByUser();

    ShoppingCartEntity findShoppingCartAdd(String idProduct);

    void deleteShoppingCart(String idShoppingCart);

    void deleteProductShoppingCart(String idProduct);

    List<ShoppingCartEntity> findAll();

    ShoppingCartEntity findShoppingCartCookie();

}
