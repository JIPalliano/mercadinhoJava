package com.example.mercadinho.domain.repository.model;

import com.example.mercadinho.controller.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "userEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity{
    @Id
    private String id;
    private String username;
    private String password;
    private String role;

    //montando o corpo da resposta da minha userEntity
    public UserResponse fromEntity(){
        return UserResponse.builder()
                .id(id)
                .username(username)
                .role(role)
                .build();
    }
}
