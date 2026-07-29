package com.bank.auth.dto;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class LoginResponse {

    private String token;
}