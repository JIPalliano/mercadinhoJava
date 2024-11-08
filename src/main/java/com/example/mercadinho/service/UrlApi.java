package com.example.mercadinho.service;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UrlApi {

//    ROUTING_API("https://api.tomtom.com/routing/1/calculateRoute"),
//    GEOCODING_API("https://api.tomtom.com/search/2/geocode");

    private final String value;

    public String getRoutingUrl(String coordinates, String key) {
        return value+"/-30.0295463,-51.203596:"+coordinates.replace(" ", "&")+"/json?key="+key;
    }

    public String getGeocodingUrl(String address, String key) {
        return value+"/"+address.replace(" ", "&")+".json?key="+key;
    }
}
