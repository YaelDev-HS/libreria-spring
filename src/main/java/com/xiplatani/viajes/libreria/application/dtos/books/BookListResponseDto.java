package com.xiplatani.viajes.libreria.application.dtos.books;

import java.util.List;

public class BookListResponseDto {
    private List<BookResponseDto> books;

    public BookListResponseDto() {
    }

    public BookListResponseDto(List<BookResponseDto> books) {
        this.books = books;
    }

    public List<BookResponseDto> getBooks() {
        return books;
    }

    public void setBooks(List<BookResponseDto> books) {
        this.books = books;
    }
}
