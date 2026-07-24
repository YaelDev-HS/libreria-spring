package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.domain.repositories.IUserRepository;
import com.xiplatani.viajes.libreria.infrastructure.entities.UserEntity;
import com.xiplatani.viajes.libreria.infrastructure.mappers.UserMapper;

@Repository
public class UserRepositoryImpl extends BaseRepositoryImpl<User, UserEntity, Long> implements IUserRepository {

    private final IJpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(IJpaUserRepository jpaUserRepository, UserMapper userMapper) {
        super(jpaUserRepository, userMapper);
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(mapper::toDomain);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}
