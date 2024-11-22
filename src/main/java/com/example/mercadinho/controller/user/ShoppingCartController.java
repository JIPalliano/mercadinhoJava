package com.example.mercadinho.controller.user;


import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
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

    @DeleteMapping(path="delete")
    public void delete() {
        this.facade.delete();
    }

    @GetMapping(path="user-shopping-cart")
    @RolesAllowed({"USER","ADMIN"})
    public ShoppingCartResponse find(){
        return this.facade.find();
    }


}
