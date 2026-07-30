package com.bank.common.event.dto;

public record LoanSubmittedEvent(
        String applicationId,
        String customerId,
        String nik,
        String customerName,
        String event

) {
}