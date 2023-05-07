package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Contract;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {
    @Override
    @EntityGraph(attributePaths = {"client, car"})
    List<Contract> findAll();
}