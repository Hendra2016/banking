package com.bank.loan.event;

public record ScoringCompletedEvent(
        String applicationId,
        Integer score,
        String decision,
        String event
) {
}