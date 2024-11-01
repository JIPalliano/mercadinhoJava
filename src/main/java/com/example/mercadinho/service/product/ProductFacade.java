package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.ProductRepository;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface ProductFacade{

    ProductEntity createProduct(ProductRequest request);

    ProductEntity updateProduct(String idProduct, ProductRequest request);

    void deleteProduct(String idProduct);

    void deleteProductShoppingCart(String idProduct);

    ProductEntity findById(ProductEntity id);

    List<ProductEntity> findAll();
}
