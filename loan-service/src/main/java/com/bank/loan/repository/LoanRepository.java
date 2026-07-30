package com.bank.loan.repository;

import com.bank.common.event.dto.ApplicationStatus;
import com.bank.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository
        extends JpaRepository<Loan, String> {

    @Modifying
    @Query("""
        update Loan l
        set l.status = :status
        where l.applicationId = :applicationId
        """)
    void updateStatus(
            @Param("applicationId") String applicationId,
            @Param("status") ApplicationStatus status);
}