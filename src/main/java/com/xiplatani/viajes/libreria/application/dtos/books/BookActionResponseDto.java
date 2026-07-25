package com.xiplatani.viajes.libreria.application.dtos.books;

public class BookActionResponseDto {
    private String message;
    private BookResponseDto book;

    public BookActionResponseDto() {
    }

    public BookActionResponseDto(String message, BookResponseDto book) {
        this.message = message;
        this.book = book;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BookResponseDto getBook() {
        return book;
    }

    public void setBook(BookResponseDto book) {
        this.book = book;
    }
}
