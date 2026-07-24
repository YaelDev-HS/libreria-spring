package com.xiplatani.viajes.libreria.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiplatani.viajes.libreria.infrastructure.entities.BookEntity;

public interface IJpaBookRepository extends JpaRepository<BookEntity, Long> {
}
