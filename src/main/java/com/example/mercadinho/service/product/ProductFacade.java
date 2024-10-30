package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.model.ProductEntity;

import java.util.List;

public interface ProductFacade{

    ProductEntity createProduct(ProductRequest request);

    ProductEntity findById(ProductEntity id);

    List<ProductEntity> findAll();
}
