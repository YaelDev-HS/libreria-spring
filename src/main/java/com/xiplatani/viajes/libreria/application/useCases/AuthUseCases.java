package com.xiplatani.viajes.libreria.application.useCases;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xiplatani.viajes.libreria.application.dtos.auth.AuthResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.auth.LoginUserDto;
import com.xiplatani.viajes.libreria.application.dtos.auth.RegisterUserDto;
import com.xiplatani.viajes.libreria.application.dtos.auth.RoleDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserDto;
import com.xiplatani.viajes.libreria.domain.exceptions.CustomException;
import com.xiplatani.viajes.libreria.domain.models.Role;
import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.domain.repositories.IRoleRepository;
import com.xiplatani.viajes.libreria.domain.repositories.IUserRepository;
import com.xiplatani.viajes.libreria.infrastructure.security.JwtService;
import com.xiplatani.viajes.libreria.infrastructure.security.UserAuth;

@Service
public class AuthUseCases {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthUseCases(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDto registerUser(RegisterUserDto dto) {
        boolean exists = userRepository.existsByEmail(dto.getEmail());

        if (exists) {
            throw CustomException.BadRequest("Esta cuenta ya esta registrada");
        }

        Optional<Role> role = this.roleRepository.findByRole("USER");

        if (role.isEmpty()) {
            throw CustomException.InternalServerError("User role not exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setName(dto.getName());
        user.setIsActive(true);
        user.setCreatedAt(new Date());
        user.setUpdatedAt(new Date());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role.get());

        User savedUser = userRepository.save(user);
        String token = jwtService.generateToken(savedUser);

        return new AuthResponseDto(token, mapToUserDto(savedUser));
    }

    public AuthResponseDto loginUser(LoginUserDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> CustomException.BadRequest("La contraseña o email no son validos"));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw CustomException.BadRequest("La contraseña o email no son validos");
        }

        if (!user.getIsActive()) {
            throw CustomException.BadRequest("Esta cuenta ha sido desactivada. Contacta soporte.");
        }

        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token, mapToUserDto(user));
    }

    public AuthResponseDto refreshToken() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof UserAuth userAuth)) {
            throw CustomException.Unauthorized("Usuario no autenticado.");
        }

        User user = userRepository.findById(userAuth.userId())
                .orElseThrow(() -> CustomException.Unauthorized("Usuario no encontrado."));

        if (!user.getIsActive()) {
            throw CustomException.BadRequest("Esta cuenta ha sido desactivada. Contacta soporte.");
        }

        String newToken = jwtService.generateToken(user);
        return new AuthResponseDto(newToken, mapToUserDto(user));
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
