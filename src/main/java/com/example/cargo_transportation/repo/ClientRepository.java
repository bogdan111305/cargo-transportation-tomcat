package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findClientById(Long id);

    Optional<Client> findClientByName(String name);

    Optional<Client> findClientByInn(String inn);

    List<Client> findAllByNameOrInnOrEmailOrRs(String name, String inn, String email, String rs);

}