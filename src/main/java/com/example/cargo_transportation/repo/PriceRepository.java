package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {
}