package com.bank.loan.kafka;

import com.bank.loan.event.LoanSubmittedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanSubmittedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(
            LoanSubmittedEvent event) {

        kafkaTemplate.send(
                "loan-submitted",
                event.applicationId(),
                event
        );
    }
}