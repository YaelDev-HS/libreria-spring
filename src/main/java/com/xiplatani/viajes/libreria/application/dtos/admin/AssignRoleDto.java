package com.xiplatani.viajes.libreria.application.dtos.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class AssignRoleDto {

    @NotBlank(message = "El campo role es requerido.")
    @Pattern(regexp = "^(?i)(LIBRARIAN|ADMIN)$", message = "El rol debe ser LIBRARIAN o ADMIN.")
    private String role;

    public AssignRoleDto() {
    }

    public AssignRoleDto(String role) {
        this.role = role;
    }

    public String getRole() {
        return role.toUpperCase();
    }

    public void setRole(String role) {
        this.role = role;
    }
}
