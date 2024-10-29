package com.example.mercadinho.controller;


import com.example.mercadinho.domain.repository.model.ProductEntity;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import com.example.mercadinho.service.ShoppingCartFacade;
import jakarta.annotation.security.RolesAllowed;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="v1/shopping-cart")
public class ShoppingCartController {


    private final ShoppingCartFacade facade;

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

    @GetMapping
    public List<ShoppingCartEntity> findAll() {
        return this.facade.findAll();
    }

    @GetMapping(path="/cookie")
    public ShoppingCartEntity findShoppingCartCookie() {
        return this.facade.findShoppingCartCookie();
    }

}
