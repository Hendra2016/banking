package com.bank.notification.service;

import com.bank.common.event.dto.ScoringCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class NotificationService {

    private final List<NotificationChannel> channels;

    public NotificationService(List<NotificationChannel> channels) {
        this.channels = channels;
    }

    public void sendNotification(
            ScoringCompletedEvent event) {
        log.info(
                """
                =================================================
                Loan Result Notification
                Application : {}
                Customer    : {}
                Score       : {}
                Decision    : {}
                =================================================
                """,
                event.applicationId(),
                event.event(),
                event.score(),
                event.decision()
        );
        channels.forEach(channel -> channel.send(
                event.applicationId(),
                event.decision()
        ));
    }
}