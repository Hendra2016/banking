package com.bank.loan.kafka;

import com.bank.common.event.dto.ApplicationStatus;
import com.bank.common.event.dto.ScoringCompletedEvent;
import com.bank.loan.entity.Loan;
import com.bank.loan.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class ScoringCompletedConsumer {

    private final LoanRepository repository;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "scoring-completed",
            groupId = "loan-service"
    )
    public void consume(
            String payload) {
        ScoringCompletedEvent event = objectMapper.readValue(
                payload,
                ScoringCompletedEvent.class
        );
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
