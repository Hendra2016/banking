package com.bank.loan.repository;

import com.bank.loan.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository
        extends JpaRepository<Loan, String> {
}