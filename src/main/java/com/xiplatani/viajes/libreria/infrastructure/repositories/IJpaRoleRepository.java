package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiplatani.viajes.libreria.infrastructure.entities.RoleEntity;

public interface IJpaRoleRepository extends JpaRepository<RoleEntity, Long> {

    Optional<RoleEntity> findByRole(String role);

}
