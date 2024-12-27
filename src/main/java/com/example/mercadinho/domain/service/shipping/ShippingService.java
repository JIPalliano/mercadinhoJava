package com.example.mercadinho.domain.service.shipping;

import com.example.mercadinho.infrastructure.integration.apitomtom.geocoding.GeocodingIntegration;
import com.example.mercadinho.infrastructure.integration.apitomtom.geocoding.response.GeocodingResponse;
import com.example.mercadinho.infrastructure.integration.apitomtom.routing.RoutingIntegration;
import com.example.mercadinho.infrastructure.integration.apitomtom.routing.response.RoutingResponse;
import com.example.mercadinho.infrastructure.integration.viacep.ViaCepIntegration;
import com.example.mercadinho.infrastructure.integration.viacep.response.ViaCepResponse;
import com.example.mercadinho.domain.service.shipping.response.ShippingResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@AllArgsConstructor
public class ShippingService {

    private final GeocodingIntegration geocodingIntegration;
    private final ViaCepIntegration viaCepIntegration;
    private final RoutingIntegration routingIntegration;

    public ShippingResponse calculateShipping(String cep) {
        ViaCepResponse address = viaCepIntegration.findAddress(cep);
        GeocodingResponse coordinates = geocodingIntegration.findCoordinates(formatAddress(address));
        RoutingResponse meters = routingIntegration.findMeters(formatCoordinates(coordinates));
        BigDecimal calculate = BigDecimal.valueOf(1.50*meters.routes().get(0).summary().lengthInMeters()/1000);
        return ShippingResponse.builder().calculate(calculate.setScale(2, RoundingMode.HALF_UP)).build();
    }

    private String formatAddress(ViaCepResponse address) {
        return address.address()
                +"%20"+address.city()
                +"%20"+address.state();
    }

    private String formatCoordinates(GeocodingResponse coordinates) {
        StringBuilder formattedCoordinates = new StringBuilder();
        coordinates.results().forEach(e ->
                formattedCoordinates.append(e.position().lat())
                        .append(",")
                        .append(e.position().lon())
        );
        return formattedCoordinates.toString();
    }

}
