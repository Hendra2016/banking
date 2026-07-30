package com.bank.common.event.dto;

public record ScoringCompletedEvent(
        String applicationId,
        Integer score,
        String decision,
        String event
) {
}