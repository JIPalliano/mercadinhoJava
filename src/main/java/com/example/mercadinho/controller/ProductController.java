package com.example.mercadinho.controller;



import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.service.product.ProductFacade;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/merchandiser")
public class ProductController {

    private final ProductFacade facade;

    @DeleteMapping(path="{id-product}")
    public void deleteProductShoppingCart(@PathVariable("id-product") String idProduct) {
        this.facade.deleteProductShoppingCart(idProduct);
    }

    @GetMapping(path="{id-product}")
    public ProductEntity findById(@PathVariable("id-product") ProductEntity idProduct) {
        return this.facade.findById(idProduct);
    }

    @PatchMapping
    public void removeQuantityProductShoppingCart(@RequestBody ProductRequest request){ //id quantity=2
        this.facade.removeQuantityProductShoppingCart(request);
    }

    @GetMapping
    public List<ProductEntity> findAll() {
        return this.facade.findAll();
    }


}
