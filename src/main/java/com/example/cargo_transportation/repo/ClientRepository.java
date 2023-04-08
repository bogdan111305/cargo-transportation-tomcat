package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientById(Long id);
}