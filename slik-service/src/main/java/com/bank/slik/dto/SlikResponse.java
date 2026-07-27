package com.bank.slik.dto;

public record SlikResponse(

        String result,
        Integer collectibility,
        Integer activeLoans

) {
    public static SlikResponse pending() {
        return new SlikResponse(
                "PENDING",
                null,
                null
        );
    }
}