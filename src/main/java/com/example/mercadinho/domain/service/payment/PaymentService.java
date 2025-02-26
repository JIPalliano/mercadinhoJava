package com.example.mercadinho.domain.service.payment;

import com.example.mercadinho.controller.request.PaymentRequest;
import com.example.mercadinho.domain.service.contractuser.UserService;
import com.example.mercadinho.infrastructure.repository.ShoppingCartRepository;
import com.example.mercadinho.infrastructure.repository.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final KafkaTemplate<String, PaymentRequest> kafkaTemplate;

    public Mono<Void> sendPayment(String payId){
        return userService.getCurrentUser()
                .flatMap(user ->
                    shoppingCartRepository.findByUserId(user.getId())
                            .flatMap(shoppingCartEntity -> {
                                int totalQuantity = shoppingCartEntity.getProducts()
                                        .stream()
                                        .mapToInt(Product::getQuantity)
                                        .sum();

                                BigDecimal totalPrice = shoppingCartEntity.getProducts()
                                        .stream()
                                        .map(product -> product.getPrice().multiply(BigDecimal.valueOf(product.getQuantity())))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add);

                                kafkaTemplate.send(
                                        "product-trend",
                                        0,
                                        "",
                                        PaymentRequest.builder()
                                                .payId(payId)
                                                .status("PENDING")
                                                .quantity(totalQuantity)
                                                .total(totalPrice)
                                                .build());
                                return shoppingCartRepository.findByUserId(user.getId());
                            })
                )
                .switchIfEmpty(Mono.error(new RuntimeException("a"))).then();
    }

}
