package com.bank.loan.service;

import com.bank.loan.dto.CreateApplicationRequest;
import com.bank.loan.entity.ApplicationStatus;
import com.bank.loan.entity.Loan;
import com.bank.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository repository;

    public Loan create(
            CreateApplicationRequest request) {

        Loan application = Loan.builder()
                .customerId(request.customerId())
                .amount(request.amount().doubleValue())
                .tenure(request.tenure())
                .status(ApplicationStatus.DRAFT)
                .build();

        return repository.save(application);
    }

    public Loan findById(Long id) {

        return
                repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Customer not found"));
    }
}