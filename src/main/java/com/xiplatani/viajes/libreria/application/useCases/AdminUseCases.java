package com.xiplatani.viajes.libreria.application.useCases;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.xiplatani.viajes.libreria.application.dtos.admin.AssignRoleDto;
import com.xiplatani.viajes.libreria.application.dtos.auth.RoleDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserDto;
import com.xiplatani.viajes.libreria.domain.exceptions.CustomException;
import com.xiplatani.viajes.libreria.domain.models.Role;
import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.domain.repositories.IRoleRepository;
import com.xiplatani.viajes.libreria.domain.repositories.IUserRepository;

@Service
public class AdminUseCases {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    public AdminUseCases(IUserRepository userRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public Map<String, Object> getAllUsers() {
        List<UserDto> users = userRepository.findAll().stream()
                .map(this::mapToUserDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("users", users);
        return response;
    }

    public Map<String, Object> assignRole(Long userId, AssignRoleDto dto) {
        String targetRoleName = dto.getRole();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.NotFound("Usuario no encontrado."));

        Role role = roleRepository.findByRole(targetRoleName)
                .orElseGet(() -> roleRepository.save(new Role(targetRoleName)));

        boolean alreadyHasRole = user.getRoles().stream()
                .anyMatch(r -> r.getRole().equalsIgnoreCase(targetRoleName));

        if (alreadyHasRole) {
            throw CustomException.BadRequest("El usuario ya cuenta con el rol " + targetRoleName + ".");
        }

        user.addRole(role);
        user.setUpdatedAt(new Date());

        User updatedUser = userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Rol " + targetRoleName + " asignado exitosamente al usuario.");
        response.put("user", mapToUserDto(updatedUser));
        return response;
    }

    public Map<String, Object> removeRole(Long userId, AssignRoleDto dto) {
        String targetRoleName = dto.getRole().toUpperCase();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.NotFound("Usuario no encontrado."));

        boolean hasRole = user.getRoles().stream()
                .anyMatch(r -> r.getRole().equalsIgnoreCase(targetRoleName));

        if (!hasRole) {
            throw CustomException.BadRequest("El usuario no tiene asignado el rol " + targetRoleName + ".");
        }

        user.removeRole(targetRoleName);
        user.setUpdatedAt(new Date());

        User updatedUser = userRepository.save(user);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Rol " + targetRoleName + " removido exitosamente del usuario.");
        response.put("user", mapToUserDto(updatedUser));
        return response;
    }

    private UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setIsActive(user.getIsActive());
        dto.setCreatedAt(user.getCreatedAt());
        dto.setUpdatedAt(user.getUpdatedAt());

        if (user.getRoles() != null) {
            List<RoleDto> roleDtos = user.getRoles().stream()
                    .map(r -> new RoleDto(r.getRole()))
                    .toList();
            dto.setRoles(roleDtos);
        }

        return dto;
    }
}
