package com.example.mercadinho.infrastructure.repository;

import com.example.mercadinho.infrastructure.repository.model.entity.UserEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;



@Repository
public interface UserRepository extends ReactiveMongoRepository<UserEntity, String> {
    //Query do mongo DB
    Mono<UserDetails> findByUsername(String username);
}
