package com.example.mercadinho.domain.service.cookies;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;


@Component
public class CookieInterceptor implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        // Ler os cookies existentes
        exchange.getRequest().getCookies().forEach((name, cookies) -> cookies
                .forEach(cookie ->
                        System.out.println("Cookie encontrado: " + cookie.getName() + " = " + cookie.getValue())));

        // Adicionar um novo cookie à resposta
        ResponseCookie newCookie = ResponseCookie.from("interceptorCookie", "cookieValue")
                .path("/mercadinho/api/v1/shoppingCart") // Caminho do cookie
                .maxAge(3600) // 1 hora
                .httpOnly(true) // Torna o cookie HttpOnly
                .build();

        // Adiciona o cookie na resposta
        exchange.getResponse().addCookie(newCookie);

        // Continua a cadeia de execução
        return chain.filter(exchange);
    }

}
