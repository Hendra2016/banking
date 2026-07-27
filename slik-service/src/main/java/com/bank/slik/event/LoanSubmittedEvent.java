package com.bank.slik.event;

public record LoanSubmittedEvent(
        String applicationId,
        String customerId,
        String nik,
        String customerName,
        String event

) {
}