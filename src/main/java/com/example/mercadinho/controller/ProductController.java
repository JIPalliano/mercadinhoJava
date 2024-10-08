package com.example.mercadinho.controller;

import com.example.mercadinho.model.ProductEntity;
import com.example.mercadinho.model.ShoppingCartEntity;
import com.example.mercadinho.repository.ProductRepository;
import com.example.mercadinho.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
        return object;
    }

    @GetMapping(path="/shopping-cart/")
    public List<ShoppingCartEntity> findAllShoppingcart() {
        return this.shoppingCart.findAll();
    }

}
