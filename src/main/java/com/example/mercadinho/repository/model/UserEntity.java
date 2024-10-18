package com.example.mercadinho.repository.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;


@Document(collection = "userEntity")
public class UserEntity {
    @Id
    private String id;
    private String name;
    private String password;

    UserEntity(){}

    public UserEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getId(){
        return id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getPassword(){
        return password;
    }
    public void setPassword(String password){
        this.password=password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity usuario)) return false;
        return Objects.equals(getPassword(), usuario.getPassword());
    }

}
