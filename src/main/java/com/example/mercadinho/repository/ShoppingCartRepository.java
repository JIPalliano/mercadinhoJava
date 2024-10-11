package com.example.mercadinho.repository;

import com.example.mercadinho.repository.model.ShoppingCartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends MongoRepository<ShoppingCartEntity, String> {
}
