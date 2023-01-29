package com.example.cargo_transportation.service;

import com.example.cargo_transportation.dto.CarDTO;
import com.example.cargo_transportation.entity.Car;
import java.util.List;

public interface CarService {
    List<CarDTO> getAllCar();

    List<CarDTO> getCarsByIds(List<Long> ids);

    Car getCarById(Long carId);

    CarDTO createCar(CarDTO carDTO);

    CarDTO updateCar(CarDTO carDTO);

    void deleteCar(Long carId);
}
