package com.example.mercadinho.controller.admin;


import com.example.mercadinho.controller.request.ProductRequest;
import com.example.mercadinho.controller.response.ErrorResponse;
import com.example.mercadinho.domain.repository.model.entity.ProductEntity;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.service.product.ProductFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@AllArgsConstructor
@RequestMapping(path="/v1/admin/merchandiser")
@Tag(name = "Produto Admin", description = "Gerenciamento de produto em nivel de admin")
public class AdminProductController {

    private final ProductFacade productFacade;

    @PutMapping(path="{id-product}" )
    @RolesAllowed("ADMIN")
    @Operation(summary = "Atualiza um produto", description = "Atualiza um produto a partir do ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização do produto feito com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao atuliazar o produto.",
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
    public Mono<ProductEntity> updateProduct(@PathVariable("id-product") String idProduct, @RequestBody ProductRequest request){
        return this.productFacade.updateProduct(idProduct, request);
    }

    @PostMapping
    @RolesAllowed("ADMIN")
    @Operation(summary = "Cria um produto", description = "Realiza a criação de um produto.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto criado com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar criar o produto.",
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
    public Mono<ProductEntity> createProduct(@RequestBody ProductRequest request) {
        return this.productFacade.createProduct(request);
    }

    @DeleteMapping(path="{id-product}")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Deleta produto", description = "Procura o produto em questão via ID e deleta.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ProductEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro ao tentar deletar o produto.",
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
    public Mono<Void> deleteProduct(@PathVariable("id-product") String idProduct){
        return this.productFacade.deleteProduct(idProduct);
    }

}
