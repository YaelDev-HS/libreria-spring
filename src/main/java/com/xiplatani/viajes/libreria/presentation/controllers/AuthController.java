package com.xiplatani.viajes.libreria.presentation.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.xiplatani.viajes.libreria.application.useCases.AuthUseCases;
import com.xiplatani.viajes.libreria.application.dtos.auth.RegisterUserDto;

import jakarta.validation.Valid;

@RestController
public class AuthController {

    private final AuthUseCases useCases;

    public AuthController(AuthUseCases useCases) {
        this.useCases = useCases;
    }

    @PostMapping("/v1/auth/register")
    public ResponseEntity<Object> RegisterUser(@RequestBody @Valid RegisterUserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                useCases.registerUser(dto));
    }

}
