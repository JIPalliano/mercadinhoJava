package com.example.mercadinho.service;

import com.example.mercadinho.repository.model.ProductEntity;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface ProductFacade{

    ProductEntity createProduct(ProductEntity request);

    ProductEntity findById(String id);

    List<ProductEntity> findAll();
}
