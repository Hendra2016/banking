package com.bank.loan.kafka;

import com.bank.common.event.dto.LoanSubmittedEvent;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanSubmittedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public void publish(
            LoanSubmittedEvent event) {
        String payload = objectMapper.writeValueAsString(event);

        System.out.println("Publishing: " + event);
        kafkaTemplate.send(
                "loan-submitted",
                event.applicationId(),
                payload
        );
        System.out.println("Publishing completed: " + event);
    }
}