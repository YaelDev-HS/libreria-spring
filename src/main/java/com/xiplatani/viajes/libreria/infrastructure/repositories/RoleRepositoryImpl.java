package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.xiplatani.viajes.libreria.domain.models.Role;
import com.xiplatani.viajes.libreria.domain.repositories.IRoleRepository;
import com.xiplatani.viajes.libreria.infrastructure.entities.RoleEntity;
import com.xiplatani.viajes.libreria.infrastructure.mappers.RoleMapper;

@Repository
public class RoleRepositoryImpl extends BaseRepositoryImpl<Role, RoleEntity, Long> implements IRoleRepository {

    private final IJpaRoleRepository jpaRoleRepository;

    public RoleRepositoryImpl(IJpaRoleRepository jpaRoleRepository, RoleMapper roleMapper) {
        super(jpaRoleRepository, roleMapper);
        this.jpaRoleRepository = jpaRoleRepository;
    }

    @Override
    public Optional<Role> findByRole(String role) {
        return jpaRoleRepository.findByRole(role)
                .map(mapper::toDomain);
    }
}
