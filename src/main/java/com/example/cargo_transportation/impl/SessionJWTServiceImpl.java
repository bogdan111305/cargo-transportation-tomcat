package com.example.cargo_transportation.impl;


import com.example.cargo_transportation.entity.SessionJWT;
import com.example.cargo_transportation.entity.User;
import com.example.cargo_transportation.exception.SessionJWTException;
import com.example.cargo_transportation.repo.SessionJWTRepository;
import com.example.cargo_transportation.repo.UserRepository;
import com.example.cargo_transportation.service.SessionJWTService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class SessionJWTServiceImpl implements SessionJWTService {
    private final SessionJWTRepository sessionJWTRepository;
    private final UserRepository userRepository;

    @Autowired
    public SessionJWTServiceImpl(SessionJWTRepository sessionJWTRepository, UserRepository userRepository) {
        this.sessionJWTRepository = sessionJWTRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SessionJWT getByUsername(String username) {
        return sessionJWTRepository.findByUserUsername(username)
                .orElseThrow(() -> new SessionJWTException("Session not found"));
    }

    @Override
    public SessionJWT saveOrUpdateSession(User user, String secretKeyString) {
        SessionJWT sessionJWT = sessionJWTRepository.findByUserUsername(user.getUsername())
                .orElse(null);

        if (sessionJWT == null) {
            sessionJWT = new SessionJWT();
            sessionJWT.setUser(user);
        }

        sessionJWT.setSecretKey(secretKeyString);

        sessionJWTRepository.save(sessionJWT);
        log.info("The session by user: {} is updated", user.getUsername());

        return sessionJWT;
    }

    @Override
    public void deleteSession(User user) {
        sessionJWTRepository.deleteByUser(user);
        log.info("The session by user: {} is deleted", user.getUsername());
    }
}
