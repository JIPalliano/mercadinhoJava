package com.example.mercadinho.domain.service.shipping;

import com.example.mercadinho.infrastructure.integration.apitomtom.geocoding.GeocodingIntegration;
import com.example.mercadinho.infrastructure.integration.apitomtom.geocoding.response.GeocodingResponse;
import com.example.mercadinho.infrastructure.integration.apitomtom.routing.RoutingIntegration;
import com.example.mercadinho.infrastructure.integration.viacep.ViaCepIntegration;
import com.example.mercadinho.infrastructure.integration.viacep.response.ViaCepResponse;
import com.example.mercadinho.domain.service.shipping.response.ShippingResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@AllArgsConstructor
public class ShippingService implements ShippingFacade {

    private final GeocodingIntegration geocodingIntegration;
    private final ViaCepIntegration viaCepIntegration;
    private final RoutingIntegration routingIntegration;

    public Mono<ShippingResponse> calculateShipping(String cep) {
        return viaCepIntegration.findAddress(cep) // Retorna um Mono<ViaCepResponse>
                .flatMap(address -> geocodingIntegration.findCoordinates(formatAddress(address))
                        .flatMap(coordinates -> routingIntegration.findMeters(formatCoordinates(coordinates))
                                .map(meters -> {
                                    BigDecimal calculate = BigDecimal.valueOf(
                                            1.50 * meters.routes().get(0).summary().lengthInMeters() / 1000
                                    );
                                    return ShippingResponse.builder()
                                            .calculate(calculate.setScale(2, RoundingMode.HALF_UP))
                                            .build();
                                })
                        )
                );
    }

    private String formatAddress(ViaCepResponse address) {
        return address.cep()
                + "%20" + address.address();
//                + "%20" + address.city()
//                + "%20" + address.state()
//                + "%20" + address.neighborhood();
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
