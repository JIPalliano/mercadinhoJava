package com.example.mercadinho.infrastructure.integration.viacep;

import com.example.mercadinho.infrastructure.integration.viacep.response.ViaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ViaCepIntegration {

    private final WebClient WebClientViaCep;

    public Mono<ViaCepResponse> findAddress(String cep){
        return WebClientViaCep
                .get()
                .uri(cep+"/json/")
                .retrieve()
                .bodyToMono(ViaCepResponse.class);
    }

}
