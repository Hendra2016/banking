package com.bank.slik.kafka;

import com.bank.slik.dto.SlikRequest;
import com.bank.slik.event.LoanSubmittedEvent;
import com.bank.slik.event.SlikCompletedEvent;
import com.bank.slik.service.SlikIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class LoanSubmittedConsumer {

    private final SlikIntegrationService service;
    private final SlikCompletedProducer producer;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "loan-submitted",
            groupId = "slik-service"
    )
    public void consume(
            String payload) {
        LoanSubmittedEvent event =
                objectMapper.readValue(
                        payload,
                        LoanSubmittedEvent.class
                );
        service.inquiry(
                event.applicationId(),
                new SlikRequest(
                        event.nik(),
                        event.customerName()
                )
        ).subscribe(response -> {
            producer.publish(
                    new SlikCompletedEvent(
                            event.applicationId(),
                            event.customerId(),
                            response.result(),
                            response.collectibility(),
                            response.activeLoans(),
                            "SLIK_COMPLETED"
                    )
            );

        });
    }
}