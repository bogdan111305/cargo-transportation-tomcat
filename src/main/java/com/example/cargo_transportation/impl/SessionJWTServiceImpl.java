package com.example.cargo_transportation.impl;


import com.example.cargo_transportation.entity.SessionJWT;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.exception.SessionJWTException;
import com.example.cargo_transportation.repo.SessionJWTRepository;
import com.example.cargo_transportation.service.SessionJWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SessionJWTServiceImpl implements SessionJWTService {
    private final SessionJWTRepository sessionJWTRepository;

    @Autowired
    public SessionJWTServiceImpl(SessionJWTRepository sessionJWTRepository) {
        this.sessionJWTRepository = sessionJWTRepository;
    }

    @Override
    public SessionJWT getByUsername(String username) {
        return sessionJWTRepository.findByUserUsername(username)
                .orElseThrow(() -> new SessionJWTException("Session not found"));
    }

    @Override
    public SessionJWT saveOrUpdateSession(User user, String secretKeyString) {
        SessionJWT sessionJWT;
        try {
            sessionJWT = getByUsername(user.getUsername());
            sessionJWT.setSecretKey(secretKeyString);
        } catch (SessionJWTException e) {
            sessionJWT = new SessionJWT();
            sessionJWT.setUser(user);
            sessionJWT.setSecretKey(secretKeyString);
        }
        return sessionJWTRepository.save(sessionJWT);
    }

    @Override
    public void deleteSession(User user) {
        sessionJWTRepository.deleteByUser(user);
    }
}
