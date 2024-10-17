package com.example.mercadinho.controller;


import com.example.mercadinho.repository.model.ProductEntity;
import com.example.mercadinho.repository.model.ShoppingCartEntity;
import com.example.mercadinho.service.ShoppingCartFacade;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/shopping-cart")
public class ShoppingCartController {


    final ShoppingCartFacade facade;

    @PostMapping(path="/{id}")
    public ShoppingCartEntity createShoppingCart(@PathVariable("id") String id, ShoppingCartEntity request) {
        return facade.createShoppingCart(id, request);
    }

    @PutMapping(path="/{id-product}/{id-shopping-cart}")
    public ShoppingCartEntity findShoppingCartAdd(@PathVariable("id-product") String idProduct,
                                                  @PathVariable("id-shopping-cart") String idShoppingCart,
                                                  HttpServletResponse response,
                                                  ProductEntity productResponse) {
        return this.facade.findShoppingCartAdd(idProduct, idShoppingCart, response, productResponse);
    }

    @GetMapping(path="/")
    public List<ShoppingCartEntity> findAll() {
        return this.facade.findAll();
    }

    @GetMapping(path="/cookie")
    public ShoppingCartEntity findShoppingCartCookie() {
        return this.facade.findShoppingCartCookie();
    }

}
