package com.xiplatani.viajes.libreria.domain.repositories;

import java.util.Optional;

import com.xiplatani.viajes.libreria.domain.models.Role;

public interface IRoleRepository extends IBaseRepository<Role> {

    Optional<Role> findByRole(String role);

}
