package com.example.mercadinho.controller.user;


import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.service.shoppingcart.ShoppingCartFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequiredArgsConstructor
@RequestMapping(path="v1/shopping-cart")
@Tag(name = "Carrinho de compras", description = "Gerenciamento de Carrinho de compras")
public class ShoppingCartController {

    private final ShoppingCartFacade facade;

    @PostMapping(path="/{id-product}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Obter produto por ID", description = "Retorna os detalhes de um produto específico.")
    public ShoppingCartEntity create(@PathVariable("id-product") String idProduct, @RequestParam Integer quantity) {
        return this.facade.create(idProduct, quantity);
    }

    @PutMapping(path="/{id-product}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartEntity addProduct(@PathVariable("id-product") String idProduct, @RequestParam Integer quantity) {
        return this.facade.addProduct(idProduct, quantity);
    }

    @DeleteMapping(path="/delete")
    @ResponseStatus(HttpStatus.OK)
    public void delete() {
        this.facade.delete();
    }

    @GetMapping(path="/user-shopping-cart")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingCartResponse find(){
        return this.facade.find();
    }


}
