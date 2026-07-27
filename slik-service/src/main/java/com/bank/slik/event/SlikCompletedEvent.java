package com.bank.slik.event;

public record SlikCompletedEvent(
        String applicationId,
        String customerId,
        String result,
        Integer collectibility,
        Integer activeLoans,
        String event
) {
}