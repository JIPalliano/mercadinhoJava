package com.example.mercadinho.domain.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestConfig {

    private static final String BASE_URL_TOMTOM = "https://api.tomtom.com/";
    private static final String BASE_URL_VIA_CEP = "https://viacep.com.br/ws/";
    // Replace with your base URL

    @Bean
    @Qualifier("tomtom")
    public RestTemplate restTemplateTomtom(RestTemplateBuilder builder) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(BASE_URL_TOMTOM);
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE); // Configure as needed

        return builder
                .uriTemplateHandler(uriBuilderFactory)
                .build();
    }

    @Bean
    @Qualifier("viacep")
    public RestTemplate restTemplateViaCep(RestTemplateBuilder builder) {
        DefaultUriBuilderFactory uriBuilderFactory = new DefaultUriBuilderFactory(BASE_URL_VIA_CEP);
        uriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE); // Configure as needed

        return builder
                .uriTemplateHandler(uriBuilderFactory)
                .build();
    }



}
