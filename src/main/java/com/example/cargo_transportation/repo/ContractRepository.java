package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract, Long> {
}