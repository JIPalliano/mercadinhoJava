package com.example.mercadinho.service.shipping;

import com.example.mercadinho.integration.apitomtom.GeocodingIntegration;
import com.example.mercadinho.integration.apitomtom.response.GeocodingResponse;
import com.example.mercadinho.integration.viacep.ViaCepIntegration;
import com.example.mercadinho.integration.viacep.response.ViaCepResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class ShippingService {

    private final GeocodingIntegration geocodingIntegration;
    private final ViaCepIntegration viaCepIntegration;

    public void calculateShipping(String cep) {
        ViaCepResponse address = viaCepIntegration.findAddress(cep);
        GeocodingResponse coordinates = geocodingIntegration.findCoordinates(formatAddress(address));
        log.info(coordinates.toString());
    }

    private String formatAddress(ViaCepResponse address) {
        return address.address()
                +"%20"+address.city()
                +"%20"+address.state();
    }

}
