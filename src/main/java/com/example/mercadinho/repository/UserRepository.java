package com.example.mercadinho.repository;

import com.example.mercadinho.repository.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {
    //Query do mongo DB
    Optional<UserEntity> findByName(String name);
}
