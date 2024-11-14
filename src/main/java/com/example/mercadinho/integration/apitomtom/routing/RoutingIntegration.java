package com.example.mercadinho.integration.apitomtom.routing;

import com.example.mercadinho.integration.apitomtom.geocoding.GeocodingIntegration;
import com.example.mercadinho.integration.apitomtom.geocoding.response.GeocodingResponse;
import com.example.mercadinho.integration.apitomtom.routing.response.RoutingResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RoutingIntegration {

    private final RestTemplate restTemplateTomtom;

    public RoutingIntegration(@Qualifier("tomtom") RestTemplate restTemplateTomtom) {
        this.restTemplateTomtom = restTemplateTomtom;
    }

    public RoutingResponse findMeters(String address){
        return restTemplateTomtom.getForObject("routing/1/calculateRoute/-30.0296814,-51.2012261:"
                +address
                +"/json?key=qaAjPn1HcClmC9b97Phtmdlo9Gld59sU", RoutingResponse.class);
    }
}
