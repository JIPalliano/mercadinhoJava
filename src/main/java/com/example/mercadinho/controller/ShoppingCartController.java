package com.example.mercadinho.controller;


import com.example.mercadinho.controller.request.ShoppingCartRequest;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import com.example.mercadinho.service.shoppingcart.ShoppingCartFacade;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="v1/shopping-cart")
public class ShoppingCartController {


    private final ShoppingCartFacade facade;

    @PostMapping(path="{id-product}")
    public ShoppingCartEntity createShoppingCart(@PathVariable("id-product") String idProduct, ShoppingCartRequest request) {
        return this.facade.createShoppingCart(idProduct, request);
    }

    @PutMapping(path="{id-product}")
    public ShoppingCartEntity findShoppingCartAdd(@PathVariable("id-product") String idProduct) {
        return this.facade.findShoppingCartAdd(idProduct);
    }

    @DeleteMapping(path="{id-shopping-cart}")
    public void deleteShoppingCart(@PathVariable("id-shopping-cart") String idShoppingCart) {
        this.facade.deleteShoppingCart(idShoppingCart);
    }

    @GetMapping(path="user-shopping-cart")
    @RolesAllowed({"USER","ADMIN"})
    public ShoppingCartEntity findShoppingCartByUser(){
        return this.facade.findShoppingCartByUser();
    }

    @GetMapping(path="/cookie")
    public ShoppingCartEntity findShoppingCartCookie() {
        return this.facade.findShoppingCartCookie();
    }

}
