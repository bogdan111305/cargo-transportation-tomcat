package com.example.cargo_transportation.service;

import com.example.cargo_transportation.modal.dto.CarRequest;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.modal.dto.CarResponse;

import java.util.List;

public interface CarService {
    CarResponse getCarBySts(String sts);

    CarResponse getCarByGosNum(String gosNum);

    CarResponse getCarById(Long carId);

    List<CarResponse> getAllCars();

    CarResponse createCar(CarRequest carRequest);

    CarResponse updateCar(CarRequest carRequest, Long carId);

    void deleteCar(Long carId);

    Car findCarById(Long carId);
}
