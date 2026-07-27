package com.bank.notification.event;

public record ScoringCompletedEvent(
        String applicationId,
        String customerId,
        Integer score,
        String decision,
        String event
) {
}