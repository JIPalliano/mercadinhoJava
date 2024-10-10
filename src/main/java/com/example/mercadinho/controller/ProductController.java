package com.example.mercadinho.controller;

import ch.qos.logback.core.model.Model;
import com.example.mercadinho.model.ProductEntity;
import com.example.mercadinho.model.ShoppingCartEntity;
import com.example.mercadinho.repository.ProductRepository;
import com.example.mercadinho.repository.ShoppingCartRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/merchandiser")
public class ProductController {

    final ProductRepository repository;
    final ShoppingCartRepository shoppingCart;


    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity request) {
        return this.repository.save(request);
    }

    @GetMapping(path="/{id}")
    public ProductEntity findById(@PathVariable("id") String id) {
        return this.repository.findById(id).orElse(null);
    }

    @GetMapping
    public List<ProductEntity> findAll() {
        return this.repository.findAll();
    }

    @PostMapping(path="/shopping-cart/{id}")
    public ShoppingCartEntity createShoppingcart(@PathVariable("id") String id, @RequestBody ShoppingCartEntity request) {
        var object = ShoppingCartEntity.builder().id(request.id()).products(request.products()).build();
        repository.findById(id).ifPresent(shoppingCart -> request.products().add(shoppingCart));
        return shoppingCart.save(object);
    }

    @GetMapping(path="/shopping-cart/{id-product}/{id-shopping-cart}")
    public ShoppingCartEntity findShoppingCartAdd(@PathVariable("id-product") String idProduct, @PathVariable("id-shopping-cart") String idShoppingCart, @RequestBody ShoppingCartEntity request) {
        var object = ShoppingCartEntity.builder().id(idShoppingCart).products(request.products()).build();
        repository.findById(idProduct).ifPresent(shoppingCart -> request.products().add(shoppingCart));
        return shoppingCart.save(object);
    }

    @GetMapping(path="/shopping-cart/")
    public List<ShoppingCartEntity> findAllShoppingcart() {
        return this.shoppingCart.findAll();
    }

    @GetMapping(path="/cookie")
    public Cookie createCookie(Model model, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie("cookie-e-bom-nada", "cookie-value");
        cookie.setMaxAge(3600);
        response.addCookie(cookie);
        return cookie;
    }

}
