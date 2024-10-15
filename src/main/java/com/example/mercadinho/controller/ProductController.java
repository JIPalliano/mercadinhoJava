package com.example.mercadinho.controller;

import com.example.mercadinho.service.cookies.CookieService;
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
    CookieService cookieService;
    final HttpServletResponse response;


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
        cookieService.createCookie(response , "cookie-e-bom-nada", "valor-do-cookie", 3600);
        return this.facade.findAll();
    }


}
