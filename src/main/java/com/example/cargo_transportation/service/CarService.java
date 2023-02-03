package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.CarDTO;
import com.example.cargo_transportation.entity.Car;
import java.util.List;

public interface CarService {
    List<CarDTO> getAllCar(List<Long> ids);

    CarDTO getCarById(Long carId);

    Car findCarById(Long carId);

    CarDTO createCar(CarDTO carDTO);

    CarDTO updateCar(CarDTO carDTO, Long carId);

    void deleteCar(Long carId);
}
