package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findCarById(Long id);

    Optional<Car> findCarByGosNum(String gosNum);

    Optional<Car> findCarBySts(String sts);

    Optional<Car> findCarByClientId(Long clientId);
}