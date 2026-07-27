package com.bank.loan.kafka;


import com.bank.loan.entity.ApplicationStatus;
import com.bank.loan.entity.Loan;
import com.bank.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.bank.loan.event.ScoringCompletedEvent;

@Component
@RequiredArgsConstructor
public class ScoringCompletedConsumer {

    private final LoanRepository repository;

    @KafkaListener(
            topics = "scoring-completed",
            groupId = "loan-service"
    )
    public void consume(
            ScoringCompletedEvent event) {

        Loan loan = repository
                .findById(event.applicationId())
                .orElseThrow();

        loan.setStatus(

                "APPROVED".equals(
                        event.decision()
                )
                        ? ApplicationStatus.APPROVED
                        : ApplicationStatus.REJECTED

        );

        repository.save(loan);
    }
}
