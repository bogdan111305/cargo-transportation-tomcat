package com.example.cargo_transportation.service;

import com.example.cargo_transportation.entity.RefreshToken;

import java.security.Principal;

public interface RefreshTokenService {
    RefreshToken findByToken(String token);
    RefreshToken saveRefreshToken(String refreshTokenString, Principal principal);
    void deleteToken(RefreshToken token);
}
