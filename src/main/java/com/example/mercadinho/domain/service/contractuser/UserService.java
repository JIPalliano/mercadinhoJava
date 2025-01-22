package com.example.mercadinho.domain.service.contractuser;

import com.example.mercadinho.controller.request.LoginRequest;
import com.example.mercadinho.controller.request.UserRequest;
import com.example.mercadinho.controller.response.LoginResponse;
import com.example.mercadinho.controller.response.UserResponse;
import com.example.mercadinho.domain.service.cookies.CookieService;
import com.example.mercadinho.infrastructure.repository.UserRepository;
import com.example.mercadinho.infrastructure.repository.model.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements ReactiveUserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final CookieService cookieService;

    public Mono<UserResponse> registerUser(UserRequest request){
//        cookieService.createCookie();
        return userRepository.save(UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(request.roles())
                .build()).map(UserEntity::fromEntity).subscribeOn(Schedulers.boundedElastic());
    }

    public Mono<LoginResponse> loginUser(LoginRequest request){
        return userRepository.findByUsername(request.username())
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found.")))
            .flatMap(userDetails -> {
                if (passwordEncoder.matches(request.password(), userDetails.getPassword())) {
                    return tokenService.generateToken(UserEntity.builder()
                            .username(request.username())
                            .build()).map(token ->
                            LoginResponse.builder()
                                    .username(userDetails.getUsername())
                                    .roles((List<? extends GrantedAuthority>) userDetails.getAuthorities())
                                    .token(token)
                                    .build()
                    );
                } else {
                    return Mono.error(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or password incorrect."));
                }
            });
    }

    public Mono<UserEntity> getCurrentUser(){
        return ReactiveSecurityContextHolder.getContext()
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .cast(UserEntity.class);
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .switchIfEmpty(Mono.defer(() -> Mono.error(new RuntimeException("a"))));
    }
}
