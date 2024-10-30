package com.example.mercadinho.service.product;

import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.domain.repository.model.ProductEntity;
import com.example.mercadinho.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService implements ProductFacade{

    private final ProductRepository repository;

    @Override
    public ProductEntity createProduct(ProductRequest request){
        return repository.save(ProductEntity.builder()
                .name(request.name())
                .price(request.price())
                .build());
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
