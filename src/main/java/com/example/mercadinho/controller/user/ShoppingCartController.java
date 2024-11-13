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

    @DeleteMapping(path="{id-shopping-cart}")
    public void delete(@PathVariable("id-shopping-cart") String idShoppingCart) {
        this.facade.delete(idShoppingCart);
    }

    @GetMapping(path="user-shopping-cart")
    @RolesAllowed({"USER","ADMIN"})
    public ShoppingCartResponse find(){
        return this.facade.find();
    }

//    @GetMapping(path="api")
//    @RolesAllowed({"USER","ADMIN"})
//    public Object getExampleData(@RequestBody AddressRequest address){
//        return this.facade.getExampleData(address);
//    }

//    @GetMapping(path="/cookie")
//    public ShoppingCartEntity findShoppingCartCookie() {
//        return this.facade.findShoppingCartCookie();
//    }

//    @PatchMapping(path = "/item")
//    public ShoppingCartEntity removeItemQuantity(String shoppingCartId, ProductRequest productRequest) {
//
//    }

}
