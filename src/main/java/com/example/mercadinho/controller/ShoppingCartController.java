package com.example.mercadinho.controller;


import com.example.mercadinho.model.ShoppingCartEntity;
import com.example.mercadinho.repository.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/merchandiser/Shopping-cart")
public class ShoppingCartController {


    final ShoppingCartRepository repository;

    @PostMapping(path="/{id}")
    public ShoppingCartEntity createShoppingcart(@PathVariable("id") String id, @RequestBody ShoppingCartEntity request) {
        LocalDateTime dateLocal = LocalDateTime.now();
        DateTimeFormatter dt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        var object = ShoppingCartEntity.builder().id(request.id()).products(request.products()).date(dt.format(dateLocal)).build();
        //repository.findById(id).ifPresent(shoppingCart -> request.products().add(shoppingCart));
        return repository.save(object);
    }

    @GetMapping(path="/{id-product}/{id-shopping-cart}")
    public ShoppingCartEntity findShoppingCartAdd(@PathVariable("id-product") String idProduct, @PathVariable("id-shopping-cart") String idShoppingCart, @RequestBody ShoppingCartEntity request) {
        var object = ShoppingCartEntity.builder().id(idShoppingCart).products(request.products()).build();
        //repository.findById(idProduct).ifPresent(shoppingCart -> request.products().add(shoppingCart));
        return repository.save(object);
    }

    @GetMapping(path="/")
    public List<ShoppingCartEntity> findAllShoppingcart() {
        return this.repository.findAll();
    }

}
