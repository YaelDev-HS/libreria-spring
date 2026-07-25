package com.xiplatani.viajes.libreria.infrastructure.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xiplatani.viajes.libreria.infrastructure.entities.LoanRequestEntity;

public interface IJpaLoanRequestRepository extends JpaRepository<LoanRequestEntity, Long> {

    @Query("SELECT l FROM LoanRequestEntity l JOIN FETCH l.user JOIN FETCH l.book WHERE l.user.id = :userId ORDER BY l.createdAt DESC")
    List<LoanRequestEntity> findByUserId(@Param("userId") Long userId);

    @Query("SELECT l FROM LoanRequestEntity l JOIN FETCH l.user JOIN FETCH l.book WHERE l.status = :status ORDER BY l.createdAt DESC")
    List<LoanRequestEntity> findByStatus(@Param("status") String status);

    @Query("SELECT l FROM LoanRequestEntity l JOIN FETCH l.user JOIN FETCH l.book WHERE l.status = :status ORDER BY l.createdAt DESC")
    List<LoanRequestEntity> findByStatusOrderByCreatedAtDesc(@Param("status") String status);

    @Query("SELECT l FROM LoanRequestEntity l JOIN FETCH l.user JOIN FETCH l.book ORDER BY l.createdAt DESC")
    List<LoanRequestEntity> findAllByOrderByCreatedAtDesc();

    @Query("SELECT COUNT(l) FROM LoanRequestEntity l WHERE l.user.id = :userId AND l.status = 'APPROVED'")
    Long countActiveLoansByUserId(@Param("userId") Long userId);

    boolean existsByUserIdAndBookIdAndStatus(Long userId, Long bookId, String status);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("UPDATE LoanRequestEntity l SET l.status = 'REJECTED', l.responseDate = :now, l.updatedAt = :now WHERE l.book.id = :bookId AND l.id != :requestId AND l.status = 'PENDING'")
    int rejectOtherPendingRequestsByBookId(@Param("bookId") Long bookId, @Param("requestId") Long requestId, @Param("now") Date now);

}
