package com.xiplatani.viajes.libreria.application.dtos.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginUserDto {

    @NotBlank(message = "El correo electronico es requerido.")
    @Email(message = "El formato del correo electronico no es valido.")
    private String email;

    @NotBlank(message = "La contraseña es requerida.")
    private String password;

    public String getEmail() {
        return email.toLowerCase();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
