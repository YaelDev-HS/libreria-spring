package com.xiplatani.viajes.libreria.domain.services;

import java.util.List;

import com.xiplatani.viajes.libreria.domain.repositories.IBaseRepository;

public abstract class BaseDomainService<D, R extends IBaseRepository<D>> {

    protected final R repository;

    public BaseDomainService(R repository) {
        this.repository = repository;
    }

    public D save(D model) {
        return repository.save(model);
    }

    public D update(D model) {
        return repository.update(model);
    }

    public D findById(Long id) {
        return repository.findById(id);
    }

    public List<D> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.delete(id);
    }
}
