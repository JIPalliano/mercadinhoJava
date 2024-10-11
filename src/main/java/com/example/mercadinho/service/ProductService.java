package com.example.mercadinho.service;

import com.example.mercadinho.repository.model.ProductEntity;
import com.example.mercadinho.repository.ProductRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService implements ProductFacade{

    final ProductRepository repository;

    @Override
    public ProductEntity createProduct(ProductEntity request){
        return repository.save(request);
    }

    @Override
    public ProductEntity findById(String id){
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ProductEntity> findAll(){
        return repository.findAll();
    }

}
