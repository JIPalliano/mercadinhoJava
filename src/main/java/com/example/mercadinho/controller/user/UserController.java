package com.example.mercadinho.controller.user;

import com.example.mercadinho.controller.request.LoginRequest;
import com.example.mercadinho.controller.request.UserRequest;
import com.example.mercadinho.controller.response.LoginResponse;
import com.example.mercadinho.controller.response.UserResponse;
import com.example.mercadinho.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse registerUser(@RequestBody UserRequest request){
        return userService.registerUser(request);
    }

}
