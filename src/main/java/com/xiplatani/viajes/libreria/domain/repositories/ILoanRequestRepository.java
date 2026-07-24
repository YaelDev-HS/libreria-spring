package com.xiplatani.viajes.libreria.domain.repositories;

import java.util.List;

import com.xiplatani.viajes.libreria.domain.models.LoanRequest;

public interface ILoanRequestRepository extends IBaseRepository<LoanRequest> {

    List<LoanRequest> findByUserId(Long userId);

    List<LoanRequest> findByStatus(String status);

    Long countActiveLoansByUserId(Long userId);

}
