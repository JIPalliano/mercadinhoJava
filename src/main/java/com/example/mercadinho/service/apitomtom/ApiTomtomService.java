package com.example.mercadinho.service.apitomtom;

import com.example.mercadinho.controller.request.AddressRequest;
import com.example.mercadinho.integration.apitomtom.GeocodingIntegration;
import com.example.mercadinho.integration.apitomtom.response.GeocodingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApiTomtomService {

    private final GeocodingIntegration geocodingIntegration;

    public GeocodingResponse getCoordnates(AddressRequest request) {
        return this.geocodingIntegration.findCoordinates(request.address());
    }

}
