package com.example.cargo_transportation.controller;

import com.example.cargo_transportation.modal.dto.CarRequest;
import com.example.cargo_transportation.modal.dto.CarResponse;
import com.example.cargo_transportation.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/car")
public class CarRestController {
    private final CarService carService;

    @Autowired
    public CarRestController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/cars")
    public List<CarResponse> getAllCar() {
        return carService.getAllCar();
    }

    @GetMapping("/findBySTS/{sts}")
    public CarResponse getCarBySts(@PathVariable("sts") String sts) {
        return carService.getCarBySts(sts);
    }

    @GetMapping("/findByGosNum/{gosNum}")
    public CarResponse getCarByGosNum(@PathVariable("gosNum") String gosNum) {
        return carService.getCarByGosNum(gosNum);
    }

    @GetMapping("/{carId}")
    public CarResponse getCarById(@PathVariable Long carId) {
        return carService.getCarById(carId);
    }

    @PostMapping()
    public CarResponse createCar(@Valid @RequestBody CarRequest car) {
        return carService.createCar(car);
    }

    @PutMapping("/{carId}")
    public CarResponse updateCar(@Valid @RequestBody CarRequest car, @PathVariable Long carId) {
        return carService.updateCar(car, carId);
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
    }
}
