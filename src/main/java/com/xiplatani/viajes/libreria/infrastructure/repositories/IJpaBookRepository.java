package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xiplatani.viajes.libreria.infrastructure.entities.BookEntity;

public interface IJpaBookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByBorrowedById(Long userId);

    Long countByBorrowedById(Long userId);

}
