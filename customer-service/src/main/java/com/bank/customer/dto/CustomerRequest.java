package com.bank.customer.dto;

import jakarta.validation.constraints.NotBlank;

public record CustomerRequest(

        @NotBlank
        String nik,

        @NotBlank
        String name,

        String phone

) {
}