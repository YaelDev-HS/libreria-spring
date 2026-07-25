package com.xiplatani.viajes.libreria.application.dtos.loans;

import java.util.List;

public class LoanRequestListResponseDto {
    private List<LoanRequestResponseDto> loanRequests;

    public LoanRequestListResponseDto() {
    }

    public LoanRequestListResponseDto(List<LoanRequestResponseDto> loanRequests) {
        this.loanRequests = loanRequests;
    }

    public List<LoanRequestResponseDto> getLoanRequests() {
        return loanRequests;
    }

    public void setLoanRequests(List<LoanRequestResponseDto> loanRequests) {
        this.loanRequests = loanRequests;
    }
}
