package com.example.mercadinho.controller.user;


import com.example.mercadinho.controller.response.ErrorResponse;
import com.example.mercadinho.controller.response.ShoppingCartResponse;
import com.example.mercadinho.infrastructure.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.service.shoppingcart.ShoppingCartFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path="v1/shopping-cart")
@Tag(name = "Carrinho de compras", description = "Gerenciamento de Carrinho de compras")
public class ShoppingCartController {

    private final ShoppingCartFacade facade;

    @PostMapping(path="/{id-product}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Cria um carrinho de compras", description = "Cria o carrinho de compras a partir do produto adicionado no carrinho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cria carrinho de compras.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro na criação.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Carrinho não encontrado no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})

    public Mono<ShoppingCartEntity> create(@PathVariable("id-product") String idProduct, @RequestParam Integer quantity) {
        return this.facade.create(idProduct, quantity);
    }

    @PutMapping(path="/{id-product}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Adiciona um produto por ID no carrinho", description = "Adiciona um novo produto ao carrinho ou atualiza o valor.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Adiciona um novo produto ao carrinho ou atualiza o valor.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro na atualização.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Carrinho não encontrado no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})

    public Mono<ShoppingCartEntity> addProduct(@PathVariable("id-product") String idProduct, @RequestParam Integer quantity) {
        return this.facade.addProduct(idProduct, quantity);
    }

    @DeleteMapping(path="/delete")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deleta o carrinho de compras", description = "Deleta a partir da sessão do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleta a partir da sessão do usuário.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro no deletar o carrinho.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Carrinho não encontrado no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})

    public Mono<Void> delete() {
        return this.facade.delete();
    }

    @GetMapping(path="/user-shopping-cart")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Procura um carrinho de compras", description = "Procura a partir da sessão do usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Procura a partir da sessão do usuário.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao encontrar o carrinho.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Carrinho não encontrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Carrinho não encontrado no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})

    public Mono<ShoppingCartResponse> find(){
        return this.facade.find();
    }


}
