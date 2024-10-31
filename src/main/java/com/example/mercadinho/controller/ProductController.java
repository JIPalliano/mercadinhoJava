package com.example.mercadinho.controller;


import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.service.product.ProductFacade;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/merchandiser")
public class ProductController {

    final ProductFacade facade;


    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductRequest request) {
        return this.facade.createProduct(request);
    }

    @PutMapping(path="{id-product}" )
    @RolesAllowed("ADMIN")
    public ProductEntity updateProduct(@PathVariable("id-product") String idProduct, @RequestBody ProductRequest request){
        return this.facade.updateProduct(idProduct, request);
    }

    @DeleteMapping(path="{id-product}")
    @RolesAllowed("ADMIN")
    public void deleteProduct(@PathVariable("id-product") String idProduct){
        this.facade.deleteProduct(idProduct);
    }

    @GetMapping(path="{id-product}")
    public ProductEntity findById(@PathVariable("id-product") ProductEntity idProduct) {
        return this.facade.findById(idProduct);
    }

    @GetMapping(path="/")
    public List<ProductEntity> findAll() {
        return this.facade.findAll();
    }


}
