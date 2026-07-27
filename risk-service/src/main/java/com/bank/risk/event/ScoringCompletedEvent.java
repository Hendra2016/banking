package com.bank.risk.event;

public record ScoringCompletedEvent(
        String applicationId,
        Integer score,
        String decision,
        String event
) {
}