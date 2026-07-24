package com.xiplatani.viajes.libreria.presentation.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xiplatani.viajes.libreria.application.dtos.books.CreateBookDto;
import com.xiplatani.viajes.libreria.application.useCases.BookUseCases;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/v1/api/books")
public class BookController {

    private final BookUseCases bookUseCases;

    public BookController(BookUseCases bookUseCases) {
        this.bookUseCases = bookUseCases;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createBook(@RequestBody @Valid CreateBookDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookUseCases.createBook(dto));
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllBooks() {
        return ResponseEntity.ok(bookUseCases.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookUseCases.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Object>> updateBook(@PathVariable Long id,
            @RequestBody @Valid CreateBookDto dto) {
        return ResponseEntity.ok(bookUseCases.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookUseCases.deleteBook(id));
    }

    @PostMapping("/{id}/request-loan")
    public ResponseEntity<Map<String, Object>> requestLoan(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookUseCases.requestLoan(id));
    }

    @PostMapping("/requests/{requestId}/approve")
    public ResponseEntity<Map<String, Object>> approveLoanRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(bookUseCases.approveLoanRequest(requestId));
    }

    @PostMapping("/requests/{requestId}/reject")
    public ResponseEntity<Map<String, Object>> rejectLoanRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(bookUseCases.rejectLoanRequest(requestId));
    }

    @PostMapping("/requests/{requestId}/return")
    public ResponseEntity<Map<String, Object>> returnLoanRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(bookUseCases.returnLoanRequest(requestId));
    }

    @GetMapping("/requests/my-requests")
    public ResponseEntity<Map<String, Object>> getMyLoanRequests() {
        return ResponseEntity.ok(bookUseCases.getMyLoanRequests());
    }

    @GetMapping("/requests/pending")
    public ResponseEntity<Map<String, Object>> getPendingLoanRequests() {
        return ResponseEntity.ok(bookUseCases.getPendingLoanRequests());
    }
}
