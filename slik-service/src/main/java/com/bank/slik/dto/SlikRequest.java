package com.bank.slik.dto;

import jakarta.validation.constraints.NotBlank;

public record SlikRequest(

        @NotBlank String nik,
        @NotBlank String name

) {
}