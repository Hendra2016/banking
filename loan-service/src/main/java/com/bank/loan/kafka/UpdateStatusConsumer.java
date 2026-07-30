package com.bank.loan.kafka;


import com.bank.common.event.dto.ApplicationStatusEvent;
import com.bank.loan.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class UpdateStatusConsumer {

    private final LoanService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "failed-process",
            groupId = "loan-service"
    )
    public void consume(
            String payload) {
        ApplicationStatusEvent event = objectMapper.readValue(
                payload,
                ApplicationStatusEvent.class
        );
        log.warn("application id {} from source {} status update to {} Reason {}", event.applicationId(),event.source(), event.status(), event.reason());
        service.updateLoan(event.applicationId(), event.status());
    }
}
