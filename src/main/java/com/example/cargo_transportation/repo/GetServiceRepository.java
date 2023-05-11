package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.ProvideService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GetServiceRepository extends JpaRepository<ProvideService, Long> {
}