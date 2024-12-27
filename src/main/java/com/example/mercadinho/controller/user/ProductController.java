package com.example.mercadinho.controller.user;



import com.example.mercadinho.controller.response.ErrorResponse;
import com.example.mercadinho.domain.service.product.ProductFacade;
import com.example.mercadinho.infrastructure.repository.model.entity.ProductEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/merchandiser")
@Tag(name = "Produto", description = "Gerenciamento de produto")
public class ProductController {

    private final ProductFacade facade;

    @GetMapping(path="{id-product}")
    @Operation(summary = "Procura produto", description = "Procura o produto a partir do ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro na criação do produto.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Produto não encontrado no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    public Mono<ProductEntity> findById(@PathVariable("id-product") ProductEntity idProduct) {
        return facade.findById(idProduct);
    }

    @GetMapping
    @Operation(summary = "Encontra os produtos", description = "Encontra todos os produtos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Todos produtos encontrados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao encontrar os produtos.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Produtos não encontrados.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Produtos não encontrados no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    public Flux<ProductEntity> findAll() {
        return this.facade.findAll();
    }


}
