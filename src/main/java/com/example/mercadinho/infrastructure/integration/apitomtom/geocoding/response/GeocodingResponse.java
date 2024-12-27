package com.example.mercadinho.infrastructure.integration.apitomtom.geocoding.response;


import lombok.Builder;

import java.util.List;

@Builder
public record GeocodingResponse(
        List<Result> results
) {

    public record Result(
            Position position
    ){
        public record Position(
                Float lat,
                Float lon
        ){}
    }

}
