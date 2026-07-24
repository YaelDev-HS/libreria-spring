package com.xiplatani.viajes.libreria.application.dtos.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterUserDto extends LoginUserDto {

    @NotBlank(message = "El nombre de usuario es requerido.")
    @Size(min = 3, max = 40, message = "El nombre de usuario debe tener entre 3 y 40 caracteres.")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
