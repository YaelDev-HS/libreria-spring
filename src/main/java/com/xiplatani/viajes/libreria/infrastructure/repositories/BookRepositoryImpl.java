package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiplatani.viajes.libreria.domain.models.Book;
import com.xiplatani.viajes.libreria.domain.repositories.IBookRepository;
import com.xiplatani.viajes.libreria.infrastructure.entities.BookEntity;
import com.xiplatani.viajes.libreria.infrastructure.mappers.BookMapper;

@Repository
public class BookRepositoryImpl extends BaseRepositoryImpl<Book, BookEntity, Long> implements IBookRepository {

    private final IJpaBookRepository jpaBookRepository;

    public BookRepositoryImpl(IJpaBookRepository jpaBookRepository, BookMapper bookMapper) {
        super(jpaBookRepository, bookMapper);
        this.jpaBookRepository = jpaBookRepository;
    }

    @Override
    public List<Book> findByBorrowedByUserId(Long userId) {
        return mapper.toDomainList(jpaBookRepository.findByBorrowedById(userId));
    }

    @Override
    public Long countByBorrowedByUserId(Long userId) {
        return jpaBookRepository.countByBorrowedById(userId);
    }
}
