package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductFacade{

    Mono<ProductEntity> createProduct(ProductRequest request);

    Mono<ProductEntity> updateProduct(String idProduct, ProductRequest request);

    Mono<Void> deleteProduct(String idProduct);

//    void deleteProductShoppingCart(String idProduct);

    Mono<ProductEntity> findById(ProductEntity id);

    Flux<ProductEntity> findAll();
}
