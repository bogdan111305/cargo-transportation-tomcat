package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.dto.CarDTO;
import com.example.cargo_transportation.entity.Car;
import java.util.List;

public interface CarService {
    CarDTO getCarBySts(String sts);

    CarDTO getCarByGosNum(String gosNum);

    CarDTO getCarById(Long carId);

    List<CarDTO> getAllCar();

    CarDTO createCar(CarDTO carDTO);

    CarDTO updateCar(CarDTO carDTO, Long carId);

    void deleteCar(Long carId);

    Car findCarById(Long carId);
}
