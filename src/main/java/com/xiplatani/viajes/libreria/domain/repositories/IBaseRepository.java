package com.xiplatani.viajes.libreria.domain.repositories;

import java.util.List;

public interface IBaseRepository<D> {

    public D save(D model);

    public D update(D model);

    public D findById(Long id);

    public List<D> findAll();

    public void delete(Long id);
}
