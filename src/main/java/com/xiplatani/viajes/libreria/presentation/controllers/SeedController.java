package com.xiplatani.viajes.libreria.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiplatani.viajes.libreria.application.useCases.SeedUseCases;

@RestController
@RequestMapping("/v1/api/seed")
public class SeedController {

    private final SeedUseCases seedUseCases;

    public SeedController(SeedUseCases seedUseCases) {
        this.seedUseCases = seedUseCases;
    }

    @PostMapping
    public ResponseEntity<Void> seedAdmin() {
        seedUseCases.executeSeed();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
