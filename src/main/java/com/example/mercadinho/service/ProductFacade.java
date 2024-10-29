package com.example.mercadinho.service;

import com.example.mercadinho.domain.repository.model.ProductEntity;

import java.util.List;

public interface ProductFacade{

    ProductEntity createProduct(ProductEntity request);

    ProductEntity findById(ProductEntity id);

    List<ProductEntity> findAll();
}
