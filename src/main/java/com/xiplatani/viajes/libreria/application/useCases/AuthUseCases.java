package com.xiplatani.viajes.libreria.application.useCases;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.xiplatani.viajes.libreria.application.dtos.auth.LoginUserDto;
import com.xiplatani.viajes.libreria.application.dtos.auth.RegisterUserDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserDto;
import com.xiplatani.viajes.libreria.domain.exceptions.CustomException;
import com.xiplatani.viajes.libreria.domain.models.Role;
import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.domain.repositories.IRoleRepository;
import com.xiplatani.viajes.libreria.domain.repositories.IUserRepository;

@Service
public class AuthUseCases {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    public AuthUseCases(IUserRepository userRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public UserDto registerUser(RegisterUserDto dto) {
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
        user.setPassword(dto.getPassword());
        user.setRole(role.get());

        User savedUser = userRepository.save(user);

        return mapToUserDto(savedUser);
    }

    public UserDto loginUser(LoginUserDto dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> CustomException.BadRequest("Correo o contraseña incorrectos"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw CustomException.BadRequest("Correo o contraseña incorrectos");
        }

        return mapToUserDto(user);
    }

    private UserDto mapToUserDto(User user) {
        return new UserDto(user.getName(), user.getEmail());
    }

}
