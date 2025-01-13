package com.example.mercadinho.infrastructure.repository;

import com.example.mercadinho.infrastructure.repository.model.entity.ShoppingCartEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends ReactiveMongoRepository<ShoppingCartEntity, String> {

    Mono<ShoppingCartEntity> findByUserId(String userId);
}
