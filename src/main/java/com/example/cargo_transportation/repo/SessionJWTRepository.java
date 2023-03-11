package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.SessionJWT;
import com.example.cargo_transportation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionJWTRepository extends JpaRepository<SessionJWT, Long> {
    Optional<SessionJWT> findByUserUsername(String username);

    @Modifying
    void deleteByUser(User user);
}