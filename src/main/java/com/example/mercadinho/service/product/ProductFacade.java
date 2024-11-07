package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import java.util.List;

public interface ProductFacade{

    ProductEntity createProduct(ProductRequest request);

    ProductEntity updateProduct(String idProduct, ProductRequest request);

    void deleteProduct(String idProduct);

//    void deleteProductShoppingCart(String idProduct);

    ProductEntity findById(ProductEntity id);

    List<ProductEntity> findAll();
}
