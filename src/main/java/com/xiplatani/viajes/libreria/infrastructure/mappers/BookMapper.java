package com.xiplatani.viajes.libreria.infrastructure.mappers;

import org.springframework.stereotype.Component;

import com.xiplatani.viajes.libreria.domain.models.Book;
import com.xiplatani.viajes.libreria.infrastructure.entities.BookEntity;

@Component
public class BookMapper implements IBaseMapper<Book, BookEntity> {

    @Override
    public Book toDomain(BookEntity entity) {
        if (entity == null) {
            return null;
        }
        Book book = new Book();
        book.setId(entity.getId());
        book.setTitle(entity.getTitle());
        book.setDescription(entity.getDescription());
        book.setPages(entity.getPages());
        book.setVersion(entity.getVersion());
        book.setIsAvailable(entity.getIsAvailable());
        book.setCreatedAt(entity.getCreatedAt());
        book.setUpdatedAt(entity.getUpdatedAt());

        return book;
    }

    @Override
    public BookEntity toEntity(Book domain) {
        if (domain == null) {
            return null;
        }
        BookEntity entity = new BookEntity();
        entity.setId(domain.getId());
        entity.setTitle(domain.getTitle());
        entity.setDescription(domain.getDescription());
        entity.setPages(domain.getPages());
        entity.setVersion(domain.getVersion());
        entity.setIsAvailable(domain.getIsAvailable());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setUpdatedAt(domain.getUpdatedAt());

        return entity;
    }
}
