package com.example.mercadinho.controller;

import com.example.mercadinho.repository.model.UserEntity;
import com.example.mercadinho.security.AuthenticationService;
import com.example.mercadinho.service.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(path="/login")
public class UserController {

    final UserFacade facade;
    final private AuthenticationService authenticationService;

    @PostMapping(path="/user")
    public UserEntity createUser(@RequestBody UserEntity request){
        return this.facade.createUser(request);
    }

    @PostMapping("authenticate")
    public String authenticate(
            Authentication authentication) {
        return authenticationService.authenticate(authentication);
    }

}
