package com.bank.slik.kafka;

import com.bank.common.event.dto.LoanSubmittedEvent;
import com.bank.slik.client.CustomerClient;
import com.bank.slik.dto.CustomerResponse;
import com.bank.slik.dto.SlikRequest;
import com.bank.slik.service.SlikIntegrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
public class LoanSubmittedConsumer {

    private final SlikIntegrationService service;
    private final ObjectMapper objectMapper;
    private final CustomerClient customerClient;

    @KafkaListener(
            topics = "loan-submitted",
            groupId = "slik-service"
    )
    public void consume(
            String payload) {
        LoanSubmittedEvent event =
                objectMapper.readValue(
                        payload,
                        LoanSubmittedEvent.class);

        CustomerResponse customer =
                customerClient.getCustomer(Long.valueOf(event.customerId()));

        service.inquiry(
                event.applicationId(),
                new SlikRequest(
                        customer.getNik(),
                        customer.getName()
                )
        ).subscribe();
    }
}