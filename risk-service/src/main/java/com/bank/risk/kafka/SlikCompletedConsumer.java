package com.bank.risk.kafka;

import com.bank.risk.entity.RiskScore;
import com.bank.risk.event.ScoringCompletedEvent;
import com.bank.risk.event.SlikCompletedEvent;
import com.bank.risk.service.RiskScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlikCompletedConsumer {

    private final RiskScoringService service;
    private final ScoringCompletedProducer producer;

    @KafkaListener(
            topics = "slik-completed",
            groupId = "risk-service"
    )
    public void consume(
            SlikCompletedEvent event) {
        RiskScore score =
                service.calculate(event);
        producer.publish(
                new ScoringCompletedEvent(
                        score.getApplicationId(),
                        score.getScore(),
                        score.getDecision(),
                        "SCORING_COMPLETED"
                )
        );
    }
}