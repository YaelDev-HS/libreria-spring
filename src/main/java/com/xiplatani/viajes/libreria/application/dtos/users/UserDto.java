package com.xiplatani.viajes.libreria.application.dtos.users;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.xiplatani.viajes.libreria.application.dtos.auth.RoleDto;

public class UserDto {

    private String name;
    private String email;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
    private List<RoleDto> roles = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(String name, String email, Boolean isActive, Date createdAt, Date updatedAt, List<RoleDto> roles) {
        this.name = name;
        this.email = email;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }
}
