package com.xiplatani.viajes.libreria.domain.repositories;

import java.util.Optional;

import com.xiplatani.viajes.libreria.domain.models.User;

public interface IUserRepository extends IBaseRepository<User> {

    public Optional<User> findByEmail(String email);

    public Boolean existsByEmail(String email);

}
