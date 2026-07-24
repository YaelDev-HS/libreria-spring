package com.xiplatani.viajes.libreria.application.dtos.auth;

public class RoleDto {

    private String role;

    public RoleDto() {
    }

    public RoleDto(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
