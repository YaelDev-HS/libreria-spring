package com.xiplatani.viajes.libreria.application.dtos.auth;

import jakarta.validation.constraints.NotBlank;

public class RegisterUserDto extends LoginUserDto {

    @NotBlank
    private String username;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
