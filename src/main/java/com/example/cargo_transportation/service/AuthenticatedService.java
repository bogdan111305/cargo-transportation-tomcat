package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.payload.request.LoginRequest;
import com.example.cargo_transportation.modal.payload.response.JWTToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticatedService {
    JWTToken authenticateUser(@Valid @RequestBody LoginRequest loginRequest);
    JWTToken refreshUser(HttpServletRequest request);
    void logout(HttpServletRequest request);
}

