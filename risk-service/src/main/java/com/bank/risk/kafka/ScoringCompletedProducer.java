package com.bank.risk.kafka;

import com.bank.risk.event.ScoringCompletedEvent;
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
}