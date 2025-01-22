package com.example.mercadinho.domain.config;

import com.example.mercadinho.infrastructure.repository.UserRepository;
import com.example.mercadinho.domain.service.contractuser.TokenService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
@RequiredArgsConstructor
public class SecurityFilter implements WebFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;


    @NotNull
    @Override
    public Mono<Void> filter( @NotNull ServerWebExchange exchange,
                              @NotNull WebFilterChain chain ) {

        String token = this.recoverToken( exchange );

        if ( token != null ) {
            return tokenService.validateToken( token )
                    .flatMap( login -> userRepository.findByUsername( login )
                            .flatMap( user -> {
                                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken( user, null,
                                        user.getAuthorities());
                                return chain.filter( exchange )
                                        .contextWrite( ReactiveSecurityContextHolder
                                                .withAuthentication( authentication ));
                            })
                    )
                    .onErrorResume( e -> chain.filter( exchange ) );
        }

        return chain.filter( exchange );
    }

    private String recoverToken( ServerWebExchange exchange ) {
        String authHeader = exchange.getRequest().getHeaders().getFirst( "Authorization" );
        if ( authHeader == null ) return null;
        return authHeader.replace("Bearer ", "");
    }

}