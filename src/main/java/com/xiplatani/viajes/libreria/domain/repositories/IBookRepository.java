package com.xiplatani.viajes.libreria.domain.repositories;

import java.util.List;
import com.xiplatani.viajes.libreria.domain.models.Book;

public interface IBookRepository extends IBaseRepository<Book> {
    List<Book> findAllByOrderByCreatedAtDesc();
}
