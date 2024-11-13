package com.example.mercadinho.integration.apitomtom;

import com.example.mercadinho.integration.apitomtom.response.GeocodingResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingIntegration {

    private final RestTemplate restTemplateGeocoding;

    public GeocodingIntegration(@Qualifier("geocoding") RestTemplate restTemplateGeocoding) {
        this.restTemplateGeocoding = restTemplateGeocoding;
    }


    public GeocodingResponse findCoordinates(String address){
        return restTemplateGeocoding.getForObject("search/2/geocode/"+address.replace(" ","%20")+".json?key=qaAjPn1HcClmC9b97Phtmdlo9Gld59sU", GeocodingResponse.class);
    }

}
