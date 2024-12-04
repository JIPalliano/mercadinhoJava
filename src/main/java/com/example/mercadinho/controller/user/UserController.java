package com.example.mercadinho.controller.user;

import com.example.mercadinho.controller.request.LoginRequest;
import com.example.mercadinho.controller.request.UserRequest;
import com.example.mercadinho.controller.response.LoginResponse;
import com.example.mercadinho.controller.response.UserResponse;
import com.example.mercadinho.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
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
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Cria um carrinho de compras", description = "Cria o carrinho de compras a partir do produto adicionado no carrinho.")
    public UserResponse registerUser(@RequestBody UserRequest request){
        return userService.registerUser(request);
    }

}
