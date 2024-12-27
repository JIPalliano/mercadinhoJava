package com.example.mercadinho.infrastructure.integration.viacep;

import com.example.mercadinho.infrastructure.integration.viacep.response.ViaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ViaCepIntegration {

    private final WebClient WebClientViaCep;

    public ViaCepResponse findAddress(String cep){
        return WebClientViaCep
                .get()
                .uri(cep+"/json")
                .retrieve()
                .bodyToMono(ViaCepResponse.class)
                .block();
    }

}
