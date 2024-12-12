package com.example.mercadinho.service.user;

import com.example.mercadinho.controller.request.LoginRequest;
import com.example.mercadinho.controller.request.UserRequest;
import com.example.mercadinho.controller.response.LoginResponse;
import com.example.mercadinho.controller.response.UserResponse;
import com.example.mercadinho.domain.repository.UserRepository;
import com.example.mercadinho.domain.repository.model.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Service
@RequiredArgsConstructor
public class UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Mono<UserResponse> registerUser(UserRequest request){
        return userRepository.save(UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build()).map(UserEntity::fromEntity).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<LoginResponse> loginUser(LoginRequest request){
        return userRepository.findByUsername(request.username())
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")))
            .flatMap(userEntity -> {
                if (passwordEncoder.matches(request.password(), userEntity.stream().map(UserEntity::getPassword).findFirst().orElse("Not found"))) {
                    return Mono.just(LoginResponse.builder()
                            .username(userEntity.stream().map(UserEntity::getUsername).findFirst().orElse("Not found"))
                            .role(userEntity.stream().map(UserEntity::getRole).findFirst().orElse("Not found"))
                            .token(tokenService.generateToken(new UserEntity()))
                            .build());
                } else {
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password incorrect."));
                }
            });
    }

    public static UserEntity getCurrentUser(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (UserEntity) principal;
    }

}
