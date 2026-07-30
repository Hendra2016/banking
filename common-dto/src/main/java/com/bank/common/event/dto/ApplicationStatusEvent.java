package com.bank.common.event.dto;

public record ApplicationStatusEvent(
        String applicationId,
        String source,
        ApplicationStatus status,
        String reason
) {
}