package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiplatani.viajes.libreria.infrastructure.entities.UserEntity;

public interface IJpaUserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    boolean existsByEmail(String email);
}
