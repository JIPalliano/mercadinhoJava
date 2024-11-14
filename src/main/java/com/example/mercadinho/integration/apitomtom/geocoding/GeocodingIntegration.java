package com.example.mercadinho.integration.apitomtom.geocoding;

import com.example.mercadinho.integration.apitomtom.geocoding.response.GeocodingResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingIntegration {

    private final RestTemplate restTemplateTomtom;

    public GeocodingIntegration(@Qualifier("tomtom") RestTemplate restTemplateTomtom) {
        this.restTemplateTomtom = restTemplateTomtom;
    }

    public GeocodingResponse findCoordinates(String address){
        return restTemplateTomtom.getForObject("search/2/geocode/"
                +address.replace(" ","%20")
                +".json?key=qaAjPn1HcClmC9b97Phtmdlo9Gld59sU", GeocodingResponse.class);
    }

}
