package com.example.mercadinho.infrastructure.repository.model.entity;

import com.example.mercadinho.controller.response.UserResponse;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Document(collection = "userEntity")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity implements UserDetails {
    @Id
    private String id;
    private String username;
    @Email(regexp = ".+[@].+[\\.].+")
    private String email;
    private String password;
    private List<String> roles;

    //montando o corpo da resposta da minha userEntity
    public UserResponse fromEntity(){
        return UserResponse.builder()
                .id(id)
                .username(username)
                .email(email)
                .roles(roles)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(roles.stream().findAny().orElse("Nothing")));
    }

}
