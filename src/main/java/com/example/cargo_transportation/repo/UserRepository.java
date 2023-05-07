package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserByUsername(@Param("username") String username);

    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findUserById(@Param("id") Long id);
}
