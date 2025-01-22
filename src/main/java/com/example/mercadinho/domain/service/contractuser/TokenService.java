package com.example.mercadinho.domain.service.contractuser;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mercadinho.infrastructure.repository.model.entity.UserEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    public Mono<String> generateToken(UserEntity userEntity) {
        return Mono.defer(() -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256(secret);
                String token = JWT.create()
                        .withIssuer("mercadinho")
                        .withSubject( userEntity.getUsername() )
                        .withExpiresAt(new Date(new Date().getTime() + 60 * 100000))
                        .sign(algorithm);

                return Mono.just(token);
            } catch (Exception e) {
                return Mono.error(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Error generating JWT Token."));
            }
        });
    }

    public Mono<String> validateToken(String token) {
        return Mono.defer(() -> {
            try {
                Algorithm algorithm = Algorithm.HMAC256(secret);
                DecodedJWT jwt = JWT.require(algorithm)
                        .withIssuer("mercadinho")
                        .build()
                        .verify(token);

                String login = jwt.getSubject();
                return Mono.just(login);
            } catch (Exception e) {
                return Mono.error(new ResponseStatusException(HttpStatus.EXPECTATION_FAILED, "Error to validate token."));
            }
        });
    }

}
