package com.bank.risk.kafka;

import com.bank.common.event.dto.ScoringCompletedEvent;
import com.bank.common.event.dto.SlikCompletedEvent;
import com.bank.risk.entity.RiskScore;
import com.bank.risk.service.RiskScoringService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Slf4j
@Component
@RequiredArgsConstructor
public class SlikCompletedConsumer {

    private final RiskScoringService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "slik-completed",
            groupId = "risk-service"
    )
    public void consume(
            String payload) {
        log.info("Payload Slik Completed: {}", payload);
        SlikCompletedEvent event =
                objectMapper.readValue(
                        payload,
                        SlikCompletedEvent.class
                );
        service.calculate(event);

    }
}