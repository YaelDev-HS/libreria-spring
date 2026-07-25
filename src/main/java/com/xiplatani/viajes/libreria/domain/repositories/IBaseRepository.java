package com.xiplatani.viajes.libreria.domain.repositories;

import java.util.List;
import java.util.Optional;

public interface IBaseRepository<D> {

    public D save(D model);

    public List<D> saveAll(List<D> models);

    public D update(D model);

    public Optional<D> findById(Long id);

    public List<D> findAll();

    public void delete(Long id);
}
