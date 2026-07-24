package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiplatani.viajes.libreria.domain.repositories.IBaseRepository;
import com.xiplatani.viajes.libreria.infrastructure.mappers.IBaseMapper;

public abstract class BaseRepositoryImpl<D, E, ID> implements IBaseRepository<D> {

    protected final JpaRepository<E, ID> jpaRepository;
    protected final IBaseMapper<D, E> mapper;

    public BaseRepositoryImpl(JpaRepository<E, ID> jpaRepository, IBaseMapper<D, E> mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public D save(D model) {
        E entity = mapper.toEntity(model);
        E savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public D update(D model) {
        E entity = mapper.toEntity(model);
        E updatedEntity = jpaRepository.save(entity);
        return mapper.toDomain(updatedEntity);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<D> findById(Long id) {
        return jpaRepository.findById((ID) id)
                .map(mapper::toDomain);
    }

    @Override
    public List<D> findAll() {
        return mapper.toDomainList(jpaRepository.findAll());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void delete(Long id) {
        jpaRepository.deleteById((ID) id);
    }
}
