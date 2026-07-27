package com.bank.slik.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SlikRequest(

        @NotBlank @Size(min = 16, max = 16) String nik,
        @NotBlank String name

) {
}