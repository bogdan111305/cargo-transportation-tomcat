package com.example.cargo_transportation.impl;

import java.security.Principal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import com.example.cargo_transportation.entity.RefreshToken;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.exception.TokenRefreshException;
import com.example.cargo_transportation.repo.RefreshTokenRepository;
import com.example.cargo_transportation.security.JWTTokenProvider;
import com.example.cargo_transportation.service.RefreshTokenService;
import com.example.cargo_transportation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private RefreshTokenRepository refreshTokenRepository;
    private UserService userService;

    @Autowired
    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userService = userService;
    }

    public RefreshToken findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new EntityNotFoundException("Not found refresh token"));
    }

    public RefreshToken saveRefreshToken(String refreshTokenString, Principal principal) {
        RefreshToken refreshToken = new RefreshToken();

        User user = userService.getUserByPrincipal(principal);

        refreshToken.setRefreshToken(refreshTokenString);
        refreshToken.setUser(user);

        return refreshTokenRepository.save(refreshToken);
    }

    public void deleteToken(RefreshToken refreshToken) {
        refreshTokenRepository.delete(refreshToken);
    }
}
