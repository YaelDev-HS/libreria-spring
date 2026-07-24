package com.xiplatani.viajes.libreria.domain.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {

    private Long id;
    private String name;
    private String email;
    private String password;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
    private List<Role> roles = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void setRole(Role role) {
        addRole(role);
    }

    public void addRole(Role role) {
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        boolean exists = this.roles.stream().anyMatch(r -> r.getRole().equalsIgnoreCase(role.getRole()));
        if (!exists) {
            this.roles.add(role);
        }
    }

    public void removeRole(String roleName) {
        if (this.roles != null) {
            this.roles.removeIf(r -> r.getRole().equalsIgnoreCase(roleName));
        }
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

}
