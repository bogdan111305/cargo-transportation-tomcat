package com.example.cargo_transportation.repo;

import com.example.cargo_transportation.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
    Optional<Car> findCarById(Long id);

    @Query("SELECT c FROM Car c LEFT JOIN FETCH c.client WHERE c.gosNum = :gosNum")
    Optional<Car> findCarByGosNum(@Param("gosNum") String gosNum);

    @Query("SELECT c FROM Car c LEFT JOIN FETCH c.client WHERE c.sts = :sts")
    Optional<Car> findCarBySts(@Param("sts") String sts);

    Optional<Car> findCarByClientId(Long clientId);
}