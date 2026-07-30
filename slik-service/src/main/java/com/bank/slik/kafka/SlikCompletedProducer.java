package com.bank.slik.kafka;

import com.bank.common.event.dto.ApplicationStatusEvent;
import com.bank.common.event.dto.SlikCompletedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SlikCompletedProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publish(SlikCompletedEvent event) {
        kafkaTemplate.send(
                "slik-completed",
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