package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xiplatani.viajes.libreria.domain.models.LoanRequest;
import com.xiplatani.viajes.libreria.domain.repositories.ILoanRequestRepository;
import com.xiplatani.viajes.libreria.infrastructure.entities.LoanRequestEntity;
import com.xiplatani.viajes.libreria.infrastructure.mappers.LoanRequestMapper;

@Repository
public class LoanRequestRepositoryImpl extends BaseRepositoryImpl<LoanRequest, LoanRequestEntity, Long>
        implements ILoanRequestRepository {

    private final IJpaLoanRequestRepository jpaLoanRequestRepository;

    public LoanRequestRepositoryImpl(IJpaLoanRequestRepository jpaLoanRequestRepository,
            LoanRequestMapper loanRequestMapper) {
        super(jpaLoanRequestRepository, loanRequestMapper);
        this.jpaLoanRequestRepository = jpaLoanRequestRepository;
    }

    @Override
    public List<LoanRequest> findByUserId(Long userId) {
        return mapper.toDomainList(jpaLoanRequestRepository.findByUserId(userId));
    }

    @Override
    public List<LoanRequest> findByStatus(String status) {
        return mapper.toDomainList(jpaLoanRequestRepository.findByStatus(status));
    }

    @Override
    public Long countActiveLoansByUserId(Long userId) {
        return jpaLoanRequestRepository.countActiveLoansByUserId(userId);
    }

    public Boolean hasPendindBookByUserID(Long userId, Long bookId){
        return jpaLoanRequestRepository.existsByUserIdAndBookIdAndStatus(userId, bookId, "PENDING");
    }

}
