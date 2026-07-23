package com.xiplatani.viajes.libreria.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AuthController {

    @PostMapping("/v1/auth/register")
    public Object RegisterUser() {
        return new String("Hello World");
    }

}
