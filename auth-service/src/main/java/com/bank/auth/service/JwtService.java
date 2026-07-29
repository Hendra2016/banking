package com.bank.auth.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    private final String secret =
            "banking-super-secret-key-banking-super-secret-key";

    public String generateToken(
            String username,
            String role) {

        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(
                        new Date(
                                System.currentTimeMillis()
                                        + 86400000))
                .signWith(
                        Keys.hmacShaKeyFor(
                                secret.getBytes()),
                        Jwts.SIG.HS256)
                .compact();
    }
}