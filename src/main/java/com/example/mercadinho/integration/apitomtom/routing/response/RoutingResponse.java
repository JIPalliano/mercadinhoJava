package com.example.mercadinho.integration.apitomtom.routing.response;

import java.util.List;

public record RoutingResponse(
        List<Routes> routes
) {
    public record Routes(
            Summary summary
    ){
        public record Summary(
                Integer lengthInMeters
        ){}
    }
}
