package com.bank.loan.event;

public record LoanSubmittedEvent(
        String applicationId,
        String customerId,
        String event
) {
}