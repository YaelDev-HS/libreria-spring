package com.xiplatani.viajes.libreria.application.dtos.loans;

public class LoanRequestActionResponseDto {
    private String message;
    private LoanRequestResponseDto loanRequest;

    public LoanRequestActionResponseDto() {
    }

    public LoanRequestActionResponseDto(String message, LoanRequestResponseDto loanRequest) {
        this.message = message;
        this.loanRequest = loanRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LoanRequestResponseDto getLoanRequest() {
        return loanRequest;
    }

    public void setLoanRequest(LoanRequestResponseDto loanRequest) {
        this.loanRequest = loanRequest;
    }
}
