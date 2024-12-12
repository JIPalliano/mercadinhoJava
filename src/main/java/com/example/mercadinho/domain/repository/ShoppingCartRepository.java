package com.example.mercadinho.domain.repository;

import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends ReactiveMongoRepository<ShoppingCartEntity, String> {

    Mono<Optional<ShoppingCartEntity>> findByUserId(String userId);
}
