package com.xiplatani.viajes.libreria.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiplatani.viajes.libreria.application.dtos.auth.AuthResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.auth.LoginUserDto;
import com.xiplatani.viajes.libreria.application.dtos.auth.RegisterUserDto;
import com.xiplatani.viajes.libreria.application.useCases.AuthUseCases;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/api/auth")
public class AuthController {

    private final AuthUseCases useCases;

    public AuthController(AuthUseCases useCases) {
        this.useCases = useCases;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(@RequestBody @Valid RegisterUserDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                useCases.registerUser(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> loginUser(@RequestBody @Valid LoginUserDto dto) {
        return ResponseEntity.status(HttpStatus.OK).body(
                useCases.loginUser(dto));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken() {
        return ResponseEntity.ok(useCases.refreshToken());
    }

}
