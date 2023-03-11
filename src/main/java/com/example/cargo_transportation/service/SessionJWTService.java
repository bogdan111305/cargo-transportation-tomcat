package com.example.cargo_transportation.service;

import com.example.cargo_transportation.entity.SessionJWT;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.exception.SessionJWTException;

import java.security.Principal;
import java.util.Optional;

public interface SessionJWTService {
    SessionJWT getByUsername(String username);
    SessionJWT saveOrUpdateSession(User user, String secretKeyString);
    void deleteSession(User user);
}
