package com.xiplatani.viajes.libreria.application.useCases;

import com.xiplatani.viajes.libreria.application.dtos.auth.RegisterUserDto;
import com.xiplatani.viajes.libreria.domain.exceptions.CustomException;
import com.xiplatani.viajes.libreria.domain.services.UserService;

import com.xiplatani.viajes.libreria.domain.models.User;

import java.util.HashMap;

import org.springframework.stereotype.Service;

@Service
public class AuthUseCases {

    private final UserService service;

    public AuthUseCases(UserService service) {
        this.service = service;
    }

    public Object registerUser(RegisterUserDto user) {
        boolean ok = service.existsByEmail(user.getEmail());

        if (ok) {
            throw CustomException.BadRequest("Esta cuenta ya esta registrada");
        }

        User userDb = new User();
        userDb.setEmail(user.getEmail());
        userDb.setName(user.getUsername());
        userDb.setPassword(user.getPassword());

        userDb = service.save(userDb);

        HashMap<String, Object> res = new HashMap<>();
        res.put("user", userDb);

        return res;
    }

}
