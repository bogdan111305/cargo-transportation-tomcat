package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepository extends JpaRepository<Service, Long> {
}