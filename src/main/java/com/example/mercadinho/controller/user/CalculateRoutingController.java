package com.example.mercadinho.controller.user;

import com.example.mercadinho.controller.request.AddressRequest;
import com.example.mercadinho.integration.apitomtom.response.GeocodingResponse;
import com.example.mercadinho.service.apitomtom.ApiTomtomService;
import com.example.mercadinho.service.shipping.ShippingService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/calculate")
public class CalculateRoutingController {

    private final ApiTomtomService tomtomService;
    private final ShippingService shippingService;

//    @GetMapping("/")
//    public GeocodingResponse routing(@RequestBody AddressRequest request) {
//        return this.tomtomService.getCoordnates(request);
//    }

    @GetMapping
    public void routing(@RequestParam String cep) {
        shippingService.calculateShipping(cep);
    }



}
