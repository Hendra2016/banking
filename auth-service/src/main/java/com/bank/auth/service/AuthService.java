package com.bank.auth.service;

import com.bank.auth.dto.LoginRequest;
import com.bank.auth.dto.LoginResponse;
import com.bank.auth.entity.User;
import com.bank.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;

    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtService.generateToken(user.getUsername(), user.getRole());
        return LoginResponse.builder().token(token).build();
    }
}