package com.xiplatani.viajes.libreria.application.useCases;

import com.xiplatani.viajes.libreria.application.dtos.auth.RegisterUserDto;
import org.springframework.stereotype.Service;

@Service
public class AuthUseCases {

    public Object registerUser(RegisterUserDto user){
        return new String("Hello World");
    }

}
