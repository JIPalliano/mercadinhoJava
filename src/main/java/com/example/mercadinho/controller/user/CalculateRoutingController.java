package com.example.mercadinho.controller.user;

import com.example.mercadinho.controller.response.ErrorResponse;
import com.example.mercadinho.domain.service.shipping.ShippingService;
import com.example.mercadinho.domain.service.shipping.response.ShippingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/calculate")
@Tag(name = "Calcula a rota de frete", description = "Gerenciamento de produto em nivel de admin")
public class CalculateRoutingController {

    private final ShippingService shippingService;

    @GetMapping
    @Operation(summary = "Mostra rotas", description = "Mostra as rotas e informações.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rotas encontradas.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShippingResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao encontrar as rotas.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Rotas não encontradas.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Erro no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    public Mono<ShippingResponse> routing(@RequestParam String cep) {
        return shippingService.calculateShipping(cep);
    }



}
