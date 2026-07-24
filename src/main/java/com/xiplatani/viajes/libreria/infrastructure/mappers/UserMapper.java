package com.xiplatani.viajes.libreria.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.xiplatani.viajes.libreria.domain.models.User;
import com.xiplatani.viajes.libreria.infrastructure.entities.UserEntity;

@Component
public class UserMapper implements IBaseMapper<User, UserEntity> {

    @Override
    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        User user = new User();
        user.setId(entity.getId());
        user.setName(entity.getName());
        user.setEmail(entity.getEmail());
        user.setPassword(entity.getPassword());
        return user;
    }

    @Override
    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }
        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getName());
        entity.setEmail(domain.getEmail());
        entity.setPassword(domain.getPassword());
        return entity;
    }
}
