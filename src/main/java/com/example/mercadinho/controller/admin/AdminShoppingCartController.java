package com.example.mercadinho.controller.admin;


import com.example.mercadinho.domain.repository.model.ShoppingCartEntity;
import com.example.mercadinho.service.shoppingcart.ShoppingCartFacade;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/admin/shopping-cart")
public class AdminShoppingCartController {

    private final ShoppingCartFacade shoppingCartFacade;


    @GetMapping
    @RolesAllowed("ADMIN")
    public List<ShoppingCartEntity> findAll() {
        return this.shoppingCartFacade.findAll();
    }

}
