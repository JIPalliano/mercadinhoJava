package com.example.mercadinho.controller.user;

import com.example.mercadinho.service.shipping.ShippingService;
import com.example.mercadinho.service.shipping.response.ShippingResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/calculate")
public class CalculateRoutingController {

    private final ShippingService shippingService;

//    @GetMapping("/")
//    public GeocodingResponse routing(@RequestBody AddressRequest request) {
//        return this.tomtomService.getCoordnates(request);
//    }

    @GetMapping
    public ShippingResponse routing(@RequestParam String cep) {
        return shippingService.calculateShipping(cep);
    }



}
