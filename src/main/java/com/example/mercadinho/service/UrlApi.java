package com.example.mercadinho.service;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlApi {

    private final String routingApi = "https://api.tomtom.com/routing/1/calculateRoute";
    private final String geocodingApi = "https://api.tomtom.com/search/2/geocode";

    public String getRoutingUrl(String coordinates, String key) {
        return getRoutingApi()+"/-30.0295463,-51.203596:"+coordinates.replace(" ", "&")+"/json?key="+key;
    }

    public String getGeocodingUrl(String address, String key) {
        return getGeocodingApi()+"/"+address.replace(" ", "&")+".json?key="+key;
    }
}
