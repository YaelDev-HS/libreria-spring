package com.xiplatani.viajes.libreria.application.dtos.loans;

import java.util.Date;

import com.xiplatani.viajes.libreria.application.dtos.books.BookResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.users.UserDto;

public class LoanRequestResponseDto {

    private Long id;
    private UserDto user;
    private BookResponseDto book;
    private String status;
    private Date requestDate;
    private Date responseDate;
    private Date returnDate;
    private Date createdAt;
    private Date updatedAt;

    public LoanRequestResponseDto() {
    }

    public LoanRequestResponseDto(Long id, UserDto user, BookResponseDto book, String status, Date requestDate,
            Date responseDate, Date returnDate, Date createdAt, Date updatedAt) {
        this.id = id;
        this.user = user;
        this.book = book;
        this.status = status;
        this.requestDate = requestDate;
        this.responseDate = responseDate;
        this.returnDate = returnDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public BookResponseDto getBook() {
        return book;
    }

    public void setBook(BookResponseDto book) {
        this.book = book;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }

    public Date getResponseDate() {
        return responseDate;
    }

    public void setResponseDate(Date responseDate) {
        this.responseDate = responseDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
