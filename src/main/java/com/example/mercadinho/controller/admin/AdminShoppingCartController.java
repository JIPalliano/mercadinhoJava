package com.example.mercadinho.controller.admin;


import com.example.mercadinho.controller.response.ErrorResponse;
import com.example.mercadinho.infrastructure.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.domain.service.shoppingcart.ShoppingCartFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/admin/shopping-cart")
@Tag(name = "Carrinho de compras Admin", description = "Gerenciamento de carrinhos de compras em nivel de Admin")
public class AdminShoppingCartController {

    private final ShoppingCartFacade shoppingCartFacade;


    @GetMapping
    @RolesAllowed("ADMIN")
    @Operation(summary = "Lista todos os produtos", description = "Procura todos os produtos e faz uma listagem.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Carrinhos de compras encontrados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao encontrar os carrinhos de compras.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Carrinhos não encontrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Carrinhos não encontrado no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    public Flux<ShoppingCartEntity> findAll() {
        return this.shoppingCartFacade.findAll();
    }

}
