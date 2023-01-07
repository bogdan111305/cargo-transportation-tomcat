package com.example.cargo_transportation.service;

import com.example.cargo_transportation.payload.request.LoginRequest;
import com.example.cargo_transportation.payload.response.JWTTokenSuccessResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthenticatedService {
    JWTTokenSuccessResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest);
}

