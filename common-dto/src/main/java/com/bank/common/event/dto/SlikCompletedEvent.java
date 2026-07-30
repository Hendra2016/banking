package com.bank.common.event.dto;

public record SlikCompletedEvent(
        String applicationId,
        String customerId,
        String result,
        Integer collectibility,
        Integer activeLoans,
        String event
) {
}