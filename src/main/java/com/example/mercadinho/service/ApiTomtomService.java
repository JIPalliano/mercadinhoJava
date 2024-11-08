package com.example.mercadinho.service;

import com.example.mercadinho.controller.request.AddressRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApiTomtomService {

    @Value("${api.key.tomtom}")
    private String key;
    private final RestTemplate restTemplate;

    public String getCoordnates(AddressRequest request) {
        //Map response = restTemplate.getForObject(UrlApi.GEOCODING_API.getGeocodingUrl(request.address()), Map.class);
        Map<String, Object> response = restTemplate.getForObject(UrlApi.GEOCODING_API.getGeocodingUrl(request.address(), this.key), Map.class);
        if (response != null) {
            // Acessamos a lista de resultados
            List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
            if (results != null && !results.isEmpty()) {
                // Acessamos o primeiro resultado
                Map<String, Object> firstResult = results.get(0);
                // Acessamos o campo "position"
                return ((Map<String, Double>) firstResult.get("position")).get("lat")+","+((Map<String, Double>) firstResult.get("position")).get("lon");
            }
        }
        return null;
    }
}
