package com.xiplatani.viajes.libreria.application.useCases;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.xiplatani.viajes.libreria.application.dtos.admin.AssignRoleDto;
import com.xiplatani.viajes.libreria.application.dtos.auth.RoleDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserActionResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserListResponseDto;
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

    public UserListResponseDto getAllUsers() {
        List<UserDto> users = userRepository.findAll().stream()
                .map(this::mapToUserDto)
                .toList();

        return new UserListResponseDto(users);
    }

    public UserActionResponseDto assignRole(Long userId, AssignRoleDto dto) {
        String targetRoleName = dto.getRole();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> CustomException.NotFound("Usuario no encontrado."));

        Role role = roleRepository.findByRole(targetRoleName)
            .orElseThrow(() -> CustomException.BadRequest("Este role no esta disponible"));

        boolean alreadyHasRole = user.getRoles().stream()
                .anyMatch(r -> r.getRole().equalsIgnoreCase(targetRoleName));

        if (alreadyHasRole) {
            throw CustomException.BadRequest("El usuario ya cuenta con el rol " + targetRoleName + ".");
        }

        user.addRole(role);
        user.setUpdatedAt(new Date());

        User updatedUser = userRepository.save(user);
        return new UserActionResponseDto("Rol " + targetRoleName + " asignado exitosamente al usuario.", mapToUserDto(updatedUser));
    }

    public UserActionResponseDto removeRole(Long userId, AssignRoleDto dto) {
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
        return new UserActionResponseDto("Rol " + targetRoleName + " removido exitosamente del usuario.", mapToUserDto(updatedUser));
    }

    private UserDto mapToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
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
