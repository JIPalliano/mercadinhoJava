package com.example.mercadinho.controller;


import com.example.mercadinho.controller.request.ShoppingCartRequest;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import com.example.mercadinho.service.shoppingcart.ShoppingCartFacade;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="v1/shopping-cart")
public class ShoppingCartController {


    private final ShoppingCartFacade facade;

    @PostMapping(path="{idProduct}")
    public ShoppingCartEntity createShoppingCart(@PathVariable("idProduct") String idProduct, ShoppingCartRequest request) {
        return facade.createShoppingCart(idProduct, request);
    }

    @PutMapping(path="{id-product}/{id-shopping-cart}")
    public ShoppingCartEntity findShoppingCartAdd(@PathVariable("id-product") String idProduct,
                                                  @PathVariable("id-shopping-cart") String idShoppingCart,
                                                  HttpServletResponse response,
                                                  ProductEntity productResponse) {
        return this.facade.findShoppingCartAdd(idProduct, idShoppingCart, response, productResponse);
    }

    @GetMapping(path="user-shopping-cart")
    @RolesAllowed({"USER","ADMIN"})
    public ShoppingCartEntity findShoppingCartByUser(){
        return this.facade.findShoppingCartByUser();
    }

    @GetMapping
    @RolesAllowed("ADMIN")
    public List<ShoppingCartEntity> findAll() {
        return this.facade.findAll();
    }

    @GetMapping(path="/cookie")
    public ShoppingCartEntity findShoppingCartCookie() {
        return this.facade.findShoppingCartCookie();
    }

}
