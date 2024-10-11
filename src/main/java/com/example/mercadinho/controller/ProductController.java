package com.example.mercadinho.controller;

import com.example.mercadinho.service.ProductFacade;
import com.example.mercadinho.repository.model.ProductEntity;
import jakarta.servlet.http.HttpServletResponse;
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
    public ProductEntity findById(@PathVariable("id") String id) {
        return this.facade.findById(id);
    }

    @GetMapping(path="/")
    public List<ProductEntity> findAll() {
        return this.facade.findAll();
    }


}
