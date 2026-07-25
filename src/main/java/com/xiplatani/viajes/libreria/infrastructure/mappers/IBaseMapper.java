package com.xiplatani.viajes.libreria.infrastructure.mappers;

import java.util.ArrayList;
import java.util.List;

public interface IBaseMapper<D, E> {

    D toDomain(E entity);

    E toEntity(D domain);

    default List<D> toDomainList(List<E> entities) {
        if (entities == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(entities.stream().map(this::toDomain).toList());
    }

    default List<E> toEntityList(List<D> domains) {
        if (domains == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(domains.stream().map(this::toEntity).toList());
    }
}
