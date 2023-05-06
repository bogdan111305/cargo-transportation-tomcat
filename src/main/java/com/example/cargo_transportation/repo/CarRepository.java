package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Car;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findCarById(Long id);

    @EntityGraph(attributePaths = {"client"})
    Optional<Car> findCarByGosNum(String gosNum);

    @EntityGraph(attributePaths = {"client"})
    Optional<Car> findCarBySts(String sts);
}