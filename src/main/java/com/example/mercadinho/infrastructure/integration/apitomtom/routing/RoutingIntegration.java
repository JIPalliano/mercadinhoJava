package com.example.mercadinho.infrastructure.integration.apitomtom.routing;

import com.example.mercadinho.infrastructure.integration.apitomtom.routing.response.RoutingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class RoutingIntegration {

    private final WebClient WebClientTomtom;

    public RoutingResponse findMeters(String address){
        return WebClientTomtom
                .get()
                .uri("routing/1/calculateRoute/-30.0296814,-51.2012261:"
                        +address
                        +"/json?key=qaAjPn1HcClmC9b97Phtmdlo9Gld59sU")
                .retrieve()
                .bodyToMono(RoutingResponse.class)
                .block();
    }
}
