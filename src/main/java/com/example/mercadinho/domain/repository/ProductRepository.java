package com.example.mercadinho.domain.repository;

import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<ProductEntity, String> {

}
