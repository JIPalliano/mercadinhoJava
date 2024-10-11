package com.example.mercadinho.impl;

import com.example.mercadinho.model.ProductEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ProductFacade{

    ProductEntity createProduct(ProductEntity request);

    ProductEntity findById(String id);

    List<ProductEntity> findAll(HttpServletResponse response);
}
