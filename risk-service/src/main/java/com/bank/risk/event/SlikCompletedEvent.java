package com.bank.risk.event;

public record SlikCompletedEvent(
        String applicationId,
        String customerId,
        String result,
        Integer collectibility,
        Integer activeLoans,
        String event
) {
}