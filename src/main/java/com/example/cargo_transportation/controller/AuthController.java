package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.modal.dto.UserDTO;
import com.example.cargo_transportation.impl.AuthenticatedServiceImpl;
import com.example.cargo_transportation.modal.payload.request.LoginRequest;
import com.example.cargo_transportation.modal.payload.request.SignupRequest;
import com.example.cargo_transportation.modal.payload.response.JWTToken;
import com.example.cargo_transportation.service.AuthenticatedService;
import com.example.cargo_transportation.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;
    private final AuthenticatedService authenticatedService;

    @Autowired
    public AuthController(UserService userService, AuthenticatedServiceImpl authenticatedService) {
        this.userService = userService;
        this.authenticatedService = authenticatedService;
    }

    @PostMapping("/login")
    public JWTToken authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authenticatedService.authenticateUser(loginRequest);
    }

    @PostMapping("/refresh")
    public JWTToken refreshUser(HttpServletRequest request) {
        return authenticatedService.refreshUser(request);
    }

    @PostMapping("/register")
    public UserDTO registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return userService.createUser(signupRequest);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        authenticatedService.logout(request);
    }
}
