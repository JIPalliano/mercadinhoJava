package com.example.mercadinho.domain.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class RestConfig {

    private static final String BASE_URL_TOMTOM = "https://api.tomtom.com/";
    private static final String BASE_URL_VIA_CEP = "https://viacep.com.br/ws/";
    // Replace with your base URL

    @Bean
    @Qualifier("tomtom")
    public WebClient WebClientTomtom() {
        return WebClient.builder()
                .baseUrl(BASE_URL_TOMTOM)
                .build();
    }

    @Bean
    @Qualifier("viacep")
    public WebClient WebClientViaCep() {
        return WebClient.builder()
                .baseUrl(BASE_URL_VIA_CEP)
                .build();
    }



}
