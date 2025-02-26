package com.example.mercadinho.controller.user;

import com.example.mercadinho.domain.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping(path="/v1/pay")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public Mono<Void> sendPayment(@RequestParam String payId){
        return paymentService.sendPayment(payId);
    }


}
