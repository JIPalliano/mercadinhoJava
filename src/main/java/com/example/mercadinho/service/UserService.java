package com.example.mercadinho.service;

import com.example.mercadinho.controller.request.LoginRequest;
import com.example.mercadinho.controller.request.UserRequest;
import com.example.mercadinho.controller.response.LoginResponse;
import com.example.mercadinho.controller.response.UserResponse;
import com.example.mercadinho.domain.repository.UserRepository;
import com.example.mercadinho.domain.repository.model.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public UserResponse registerUser(UserRequest request){
        return userRepository.save(UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build()).fromEntity();
    }

    public LoginResponse loginUser(LoginRequest request){
        return userRepository.findByUsername(request.username()).map(userEntity -> {
            if(passwordEncoder.matches(request.password(), userEntity.getPassword())){
                return LoginResponse.builder()
                        .username(userEntity.getUsername())
                        .role(userEntity.getRole())
                        .token(tokenService.generateToken(userEntity))
                        .build();
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password incorrect.");
            }
        }).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found."));
    }

    public static UserEntity getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserEntity) principal;
    }

}
