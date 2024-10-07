package com.example.mercadinho.controller;

import com.example.mercadinho.model.ProductEntity;
import com.example.mercadinho.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path="/merchandiser")
public class ProductController {

    final ProductRepository repository;

    @PostMapping
    public ProductEntity createProduct(@RequestBody ProductEntity product) {
        return this.repository.save(product);
    }

    @GetMapping(path="/{id}")
    public ProductEntity findById(@PathVariable("id") String id) {
        return this.repository.findById(id).orElse(null);
    }

    @GetMapping
    public List<ProductEntity> findAll() {
        return this.repository.findAll();
    }

}
