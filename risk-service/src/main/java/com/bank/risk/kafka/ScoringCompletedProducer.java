package com.bank.risk.kafka;

import com.bank.common.event.dto.ApplicationStatusEvent;
import com.bank.common.event.dto.ScoringCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScoringCompletedProducer {

    private final KafkaTemplate<String,Object>
            kafkaTemplate;

    public void publish(
            ScoringCompletedEvent event) {
        kafkaTemplate.send(
                "scoring-completed",
                event.applicationId(),
                event
        );
    }

    public void failed(ApplicationStatusEvent event) {
        kafkaTemplate.send(
                "failed-process",
                event
        );
    }
}