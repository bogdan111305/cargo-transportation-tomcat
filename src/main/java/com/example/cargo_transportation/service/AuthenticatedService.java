package com.example.cargo_transportation.service;

import com.example.cargo_transportation.payload.request.LoginRequest;
import com.example.cargo_transportation.payload.request.RefreshToken;
import com.example.cargo_transportation.payload.response.JWTToken;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticatedService {
    JWTToken authenticateUser(@Valid @RequestBody LoginRequest loginRequest);
    JWTToken refreshUser(@Valid @RequestBody RefreshToken refreshToken);
}

