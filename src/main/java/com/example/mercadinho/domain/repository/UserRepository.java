package com.example.mercadinho.domain.repository;

import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    //Query do mongo DB
    Optional<UserEntity> findByUsername(String username);
}
