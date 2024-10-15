package com.example.mercadinho.service;

import com.example.mercadinho.repository.model.ProductEntity;
import com.example.mercadinho.repository.ProductRepository;
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
    public ProductEntity findById(ProductEntity id){
        return repository.findById(id.id()).orElse(null);
    }

    @Override
    public List<ProductEntity> findAll(){
        return repository.findAll();
    }

}
