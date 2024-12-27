package com.example.mercadinho.infrastructure.repository;

import com.example.mercadinho.infrastructure.repository.model.entity.ProductEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<ProductEntity, String> {

}
