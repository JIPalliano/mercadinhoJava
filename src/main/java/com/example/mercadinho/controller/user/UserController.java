package com.example.mercadinho.controller.user;

import com.example.mercadinho.controller.request.LoginRequest;
import com.example.mercadinho.controller.request.UserRequest;
import com.example.mercadinho.controller.response.ErrorResponse;
import com.example.mercadinho.controller.response.LoginResponse;
import com.example.mercadinho.controller.response.UserResponse;
import com.example.mercadinho.domain.repository.model.entity.ShoppingCartEntity;
import com.example.mercadinho.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
@Tag(name = "Login e registro de User", description = "Efetua o login do usuário e registra.")
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    @Operation(summary = "Cria um carrinho de compras", description = "Cria o carrinho de compras a partir do produto adicionado no carrinho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro no login.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Login não encontrado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Login não encontrado no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um carrinho de compras", description = "Cria o carrinho de compras a partir do produto adicionado no carrinho.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registro efetuado com sucesso.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ShoppingCartEntity.class))}),
            @ApiResponse(responseCode = "400", description = "Erro no registro.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Não foi possivel registrar o usuário.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Login não efetuado.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Registro não efetuado no banco.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))})})
    public UserResponse registerUser(@RequestBody UserRequest request){
        return userService.registerUser(request);
    }

}
