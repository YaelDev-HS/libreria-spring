package com.xiplatani.viajes.libreria.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiplatani.viajes.libreria.application.dtos.admin.AssignRoleDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserActionResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserListResponseDto;
import com.xiplatani.viajes.libreria.application.useCases.AdminUseCases;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/api/admin")
public class AdminController {

    private final AdminUseCases adminUseCases;

    public AdminController(AdminUseCases adminUseCases) {
        this.adminUseCases = adminUseCases;
    }

    @GetMapping("/users")
    public ResponseEntity<UserListResponseDto> getAllUsers() {
        return ResponseEntity.ok(adminUseCases.getAllUsers());
    }

    @PostMapping("/users/{userId}/roles/add")
    public ResponseEntity<UserActionResponseDto> assignRole(
            @PathVariable Long userId,
            @RequestBody @Valid AssignRoleDto dto) {
        return ResponseEntity.ok(adminUseCases.assignRole(userId, dto));
    }

    @PostMapping("/users/{userId}/roles/remove")
    public ResponseEntity<UserActionResponseDto> removeRole(
            @PathVariable Long userId,
            @RequestBody @Valid AssignRoleDto dto) {
        return ResponseEntity.ok(adminUseCases.removeRole(userId, dto));
    }
}
