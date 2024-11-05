package com.example.mercadinho.controller;


import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.controller.request.ShoppingCartRequest;
import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import com.example.mercadinho.service.shoppingcart.ShoppingCartFacade;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping(path="v1/shopping-cart")
public class ShoppingCartController {


    private final ShoppingCartFacade facade;

    @PostMapping(path="{id-product}")
    public ShoppingCartEntity create(@PathVariable("id-product") String idProduct, @RequestParam Integer quantity) {
        return this.facade.create(idProduct, quantity);
    }

    @PutMapping(path="{id-product}")
    public ShoppingCartEntity addProduct(@PathVariable("id-product") String idProduct, @RequestParam Integer quantity) {
        return this.facade.addProduct(idProduct, quantity);
    }

    @DeleteMapping(path="{id-shopping-cart}")
    public void delete(@PathVariable("id-shopping-cart") String idShoppingCart) {
        this.facade.delete(idShoppingCart);
    }

    @GetMapping(path="user-shopping-cart")
    @RolesAllowed({"USER","ADMIN"})
    public ShoppingCartEntity find(){
        return this.facade.find();
    }

//    @GetMapping(path="/cookie")
//    public ShoppingCartEntity findShoppingCartCookie() {
//        return this.facade.findShoppingCartCookie();
//    }

//    @PatchMapping(path = "/item")
//    public ShoppingCartEntity removeItemQuantity(String shoppingCartId, ProductRequest productRequest) {
//
//    }

}
