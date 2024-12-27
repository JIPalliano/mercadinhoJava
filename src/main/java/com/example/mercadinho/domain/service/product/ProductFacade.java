package com.example.mercadinho.domain.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.infrastructure.repository.model.entity.ProductEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductFacade{

    Mono<ProductEntity> createProduct(ProductRequest request);

    Mono<ProductEntity> updateProduct(String idProduct, ProductRequest request);

    Mono<Void> deleteProduct(String idProduct);

//    void deleteProductShoppingCart(String idProduct);

    Mono<ProductEntity> findById(ProductEntity id);

    Flux<ProductEntity> findAll();
}
