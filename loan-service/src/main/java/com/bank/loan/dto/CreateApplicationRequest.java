package com.bank.loan.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateApplicationRequest(

        @NotBlank(message = "Customer ID is required")
        String customerId,

        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount,

        @Positive(message = "Tenure must be greater than zero")
        int tenure

) {
}