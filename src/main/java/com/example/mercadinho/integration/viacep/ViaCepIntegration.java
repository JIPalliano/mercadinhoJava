package com.example.mercadinho.integration.viacep;

import com.example.mercadinho.integration.apitomtom.response.GeocodingResponse;
import com.example.mercadinho.integration.viacep.response.ViaCepResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service //qual faz mais sentido usar "service" ou "component"
public class ViaCepIntegration {

    private final RestTemplate restTemplateViaCep;

    public ViaCepIntegration(@Qualifier("viacep") RestTemplate restTemplateViaCep) {
        this.restTemplateViaCep = restTemplateViaCep;
    }

    public ViaCepResponse findAddress(String cep){
        return restTemplateViaCep.getForObject(cep+"/json", ViaCepResponse.class);
    }

}
