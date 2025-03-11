package com.example.mercadinho.domain.service.payment;

import com.example.avro.Payment;
import com.example.mercadinho.domain.service.contractuser.UserService;
import com.example.mercadinho.infrastructure.repository.ShoppingCartRepository;
import com.example.mercadinho.infrastructure.repository.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final UserService userService;
    private final KafkaTemplate<String, Payment> kafkaTemplate;

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
                                log.info("erro ->{}", Payment.newBuilder()
                                        .setPayId(payId)
                                        .setQuantity(totalQuantity)
                                        .setStatus("Success")
                                        .setTotal(totalPrice.toString())
                                        .build());
                                kafkaTemplate.send(
                                        "product-trend",
                                        Payment.newBuilder()
                                                .setPayId(payId)
                                                .setQuantity(totalQuantity)
                                                .setStatus("Success")
                                                .setTotal(totalPrice.toString())
                                                .build());
                                return shoppingCartRepository.findByUserId(user.getId());
                            })
                )
                .switchIfEmpty(Mono.error(new RuntimeException("a"))).then();
    }

}
