package com.xiplatani.viajes.libreria.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.xiplatani.viajes.libreria.domain.models.Role;
import com.xiplatani.viajes.libreria.infrastructure.entities.RoleEntity;

@Component
public class RoleMapper implements IBaseMapper<Role, RoleEntity> {

    @Override
    public Role toDomain(RoleEntity entity) {
        if (entity == null) {
            return null;
        }
        Role role = new Role();
        role.setId(entity.getId());
        role.setRole(entity.getRole());
        return role;
    }

    @Override
    public RoleEntity toEntity(Role domain) {
        if (domain == null) {
            return null;
        }
        RoleEntity entity = new RoleEntity();
        entity.setId(domain.getId());
        entity.setRole(domain.getRole());
        return entity;
    }
}
