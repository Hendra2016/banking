package com.bank.loan.service;

import com.bank.common.event.dto.ApplicationStatus;
import com.bank.common.event.dto.LoanSubmittedEvent;
import com.bank.loan.dto.CreateApplicationRequest;
import com.bank.loan.entity.Loan;
import com.bank.loan.kafka.LoanSubmittedProducer;
import com.bank.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository repository;
    private final LoanSubmittedProducer producer;

    public Loan create(String customerId,CreateApplicationRequest request) {

        Loan application = Loan.builder()
                .customerId(customerId)
                .amount(request.amount().doubleValue())
                .tenure(request.tenure())
                .status(ApplicationStatus.DRAFT)
                .build();

        return repository.save(application);
    }

    public Loan findById(String id) {

        return
                repository.findById(id)
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Customer not found"));
    }

    @Transactional
    public void updateLoan(String applicationId, ApplicationStatus status) {
        repository.updateStatus(applicationId, status);
    }

    @Transactional
    public Loan submit(String id) {

        Loan loan = repository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Loan not found"));

        loan.setStatus(
                ApplicationStatus.SUBMITTED
        );

        repository.save(loan);

        producer.publish(
                new LoanSubmittedEvent(
                        loan.getApplicationId().toString(),
                        loan.getCustomerId(),
                        "",
                        "",
                        "LOAN_SUBMITTED"
                )
        );

        return loan;
    }
}