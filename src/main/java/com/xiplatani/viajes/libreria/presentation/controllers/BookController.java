package com.xiplatani.viajes.libreria.presentation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.xiplatani.viajes.libreria.application.dtos.books.BookActionResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.books.BookListResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.books.CreateBookDto;
import com.xiplatani.viajes.libreria.application.dtos.common.MessageResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.loans.LoanRequestActionResponseDto;
import com.xiplatani.viajes.libreria.application.dtos.loans.LoanRequestListResponseDto;
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
    public ResponseEntity<BookActionResponseDto> createBook(@RequestBody @Valid CreateBookDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookUseCases.createBook(dto));
    }

    @GetMapping
    public ResponseEntity<BookListResponseDto> getAllBooks() {
        return ResponseEntity.ok(bookUseCases.getAllBooks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookActionResponseDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookUseCases.getBookById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookActionResponseDto> updateBook(@PathVariable Long id,
            @RequestBody @Valid CreateBookDto dto) {
        return ResponseEntity.ok(bookUseCases.updateBook(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> deleteBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookUseCases.deleteBook(id));
    }

    @PostMapping("/{id}/request-loan")
    public ResponseEntity<LoanRequestActionResponseDto> requestLoan(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookUseCases.requestLoan(id));
    }

    @PostMapping("/requests/{requestId}/approve")
    public ResponseEntity<LoanRequestActionResponseDto> approveLoanRequest(
            @PathVariable Long requestId,
            @RequestParam(name = "rejectOthers", defaultValue = "false") Boolean rejectOthers) {
        return ResponseEntity.ok(bookUseCases.approveLoanRequest(requestId, rejectOthers));
    }

    @PostMapping("/requests/{requestId}/reject")
    public ResponseEntity<LoanRequestActionResponseDto> rejectLoanRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(bookUseCases.rejectLoanRequest(requestId));
    }

    @PostMapping("/requests/{requestId}/return")
    public ResponseEntity<LoanRequestActionResponseDto> returnLoanRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok(bookUseCases.returnLoanRequest(requestId));
    }

    @GetMapping("/requests/my-requests")
    public ResponseEntity<LoanRequestListResponseDto> getMyLoanRequests() {
        return ResponseEntity.ok(bookUseCases.getMyLoanRequests());
    }

    @GetMapping("/requests")
    public ResponseEntity<LoanRequestListResponseDto> getLoanRequestsByStatus(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(bookUseCases.getLoanRequestsByStatus(status));
    }

}
