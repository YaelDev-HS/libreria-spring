package com.xiplatani.viajes.libreria.domain.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.domain.repositories.IUserRepository;

@Service
public class UserService extends BaseDomainService<User, IUserRepository> {

    public UserService(IUserRepository userRepository) {
        super(userRepository);
    }

    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }
}
