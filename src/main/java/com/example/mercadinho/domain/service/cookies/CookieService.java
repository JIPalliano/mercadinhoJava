package com.example.mercadinho.domain.service.cookies;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.session.CookieWebSessionIdResolver;
import org.springframework.web.server.session.WebSessionIdResolver;
import reactor.core.publisher.Mono;

@Service
public class CookieService {

    public Mono<ServerResponse> createCookie(ServerRequest request) {
        // Criar um cookie
        ResponseCookie cookie = ResponseCookie.from("userId", "12345") // Nome e valor do cookie
                .httpOnly(true) // Torna o cookie HttpOnly
                .secure(false) // Torna o cookie seguro (true para HTTPS)
                .path("/") // Define o caminho
                .maxAge(3600) // Define o tempo de vida (em segundos)
                .build();

        // Adicionar o cookie na resposta
        return ServerResponse.ok()
                .cookie(cookie)
                .bodyValue("Cookie foi criado com sucesso!");
    }

    @Bean
    public WebSessionIdResolver cookieSerializer() {
        CookieWebSessionIdResolver resolver = new CookieWebSessionIdResolver();
        resolver.setCookieName("ShoppingCart");
        resolver.addCookieInitializer((builder) -> builder.path("/"));
        resolver.addCookieInitializer((builder) -> builder.sameSite("Strict"));
        return resolver;
    }

}
