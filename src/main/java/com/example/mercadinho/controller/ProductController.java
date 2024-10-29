package com.example.mercadinho.controller;


import com.example.mercadinho.service.ProductFacade;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/merchandiser")
public class ProductController {

    final ProductFacade facade;


    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity request) {
        return this.facade.createProduct(request);
    }

    @GetMapping(path="/{id}")
    public ProductEntity findById(@PathVariable("id") ProductEntity id) {
        return this.facade.findById(id);
    }

    @GetMapping(path="/")
    public List<ProductEntity> findAll() {
        return this.facade.findAll();
    }


}
