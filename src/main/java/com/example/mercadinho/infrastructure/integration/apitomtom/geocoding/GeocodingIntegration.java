package com.example.mercadinho.infrastructure.integration.apitomtom.geocoding;

import com.example.mercadinho.infrastructure.integration.apitomtom.geocoding.response.GeocodingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class GeocodingIntegration {

    private final WebClient WebClientTomtom;

    public GeocodingResponse findCoordinates(String address){
        return WebClientTomtom
                .get()
                .uri("search/2/geocode/"
                        +address.replace(" ","%20")
                        +".json?key=qaAjPn1HcClmC9b97Phtmdlo9Gld59sU")
                .retrieve()
                .bodyToMono(GeocodingResponse.class).block();

    }

}
