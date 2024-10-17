package com.example.mercadinho.controller;

import com.example.mercadinho.repository.model.UserEntity;
import com.example.mercadinho.service.UserFacade;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping(path="/login")
public class UserController {

    final UserFacade facade;

    @PostMapping(path="/user")
    public UserEntity createUser(@RequestBody UserEntity request){
        return this.facade.createUser(request);
    }

}
