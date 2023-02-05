package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.dto.CarDTO;
import com.example.cargo_transportation.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
public class CarRestController {

    private final CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<CarDTO> getAllCar(@RequestBody(required = false) List<Long> ids) {
        return carService.getAllCar(ids);
    }

    @GetMapping("/{carId}")
    public CarDTO getCarById(@PathVariable Long carId) {
        return carService.getCarById(carId);
    }

    @PostMapping()
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO) {
        return carService.createCar(carDTO);
    }

    @PutMapping("/{carId}")
    public CarDTO updateCar(@Valid @RequestBody CarDTO carDTO, @PathVariable Long carId) {
        return carService.updateCar(carDTO, carId);
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
    }
}
