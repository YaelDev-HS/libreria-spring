package com.xiplatani.viajes.libreria.application.useCases;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xiplatani.viajes.libreria.domain.exceptions.CustomException;
import com.xiplatani.viajes.libreria.domain.models.Role;
import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.domain.repositories.IRoleRepository;
import com.xiplatani.viajes.libreria.domain.repositories.IUserRepository;

@Service
public class SeedUseCases {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    public SeedUseCases(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void executeSeed() {
        if (userRepository.existsByEmail(adminEmail)) {
            return;
        }

        Optional<Role> adminRole = roleRepository.findByRole("ADMIN");

        if (adminRole.isEmpty()) {
            throw CustomException.InternalServerError("No viene el role ADMIN en las migraciones SQL");
        }

        User adminUser = new User();
        adminUser.setName("Admin");
        adminUser.setEmail(adminEmail);
        adminUser.setPassword(passwordEncoder.encode(adminPassword));
        adminUser.setIsActive(true);
        adminUser.setCreatedAt(new Date());
        adminUser.setUpdatedAt(new Date());
        adminUser.setRole(adminRole.get());

        userRepository.save(adminUser);
    }

}
