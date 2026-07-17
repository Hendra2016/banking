package com.bank.customer.dto;

public record CustomerResponse(
        Long id,
        String nik,
        String name,
        String phone
) {
}