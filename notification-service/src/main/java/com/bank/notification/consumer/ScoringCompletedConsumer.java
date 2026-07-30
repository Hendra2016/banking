package com.bank.notification.consumer;


import com.bank.common.event.dto.ScoringCompletedEvent;
import com.bank.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScoringCompletedConsumer {

    private final ObjectMapper objectMapper;
    private final NotificationService service;

    @KafkaListener(
            topics = "scoring-completed",
            groupId = "notification-service"
    )
    public void consume(
            String payload) {

        ScoringCompletedEvent event =
                objectMapper.readValue(
                        payload,
                        ScoringCompletedEvent.class
                );

        service.sendNotification(event);
    }
}