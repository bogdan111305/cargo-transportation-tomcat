package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.sessionJWT LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Query("SELECT u FROM User u LEFT JOIN FETCH u.sessionJWT LEFT JOIN FETCH u.roles WHERE u.id = :id")
    Optional<User> findUserById(@Param("id") Long id);
}
