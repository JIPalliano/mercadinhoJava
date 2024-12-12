package com.example.mercadinho.domain.repository;

import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;


@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {
    //Query do mongo DB
    Mono<Optional<UserEntity>> findByUsername(String username);
}
