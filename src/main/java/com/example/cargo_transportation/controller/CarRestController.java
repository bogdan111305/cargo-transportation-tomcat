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

    @GetMapping("/findBySTS/{sts}")
    public CarDTO getCarBySts(@PathVariable("sts") String sts) {
        return carService.getCarBySts(sts);
    }

    @GetMapping("/findByGosNum/{gosNum}")
    public CarDTO getCarByGosNum(@PathVariable("gosNum") String gosNum) {
        return carService.getCarByGosNum(gosNum);
    }

    @GetMapping("/{carId}")
    public CarDTO getCarById(@PathVariable Long carId) {
        return carService.getCarById(carId);
    }

    @PostMapping()
    public CarDTO createCar(@Valid @RequestBody CarDTO car) {
        return carService.createCar(car);
    }

    @PutMapping("/{carId}")
    public CarDTO updateCar(@Valid @RequestBody CarDTO car, @PathVariable Long carId) {
        return carService.updateCar(car, carId);
    }

    @DeleteMapping("/{carId}")
    public void deleteCar(@PathVariable Long carId) {
        carService.deleteCar(carId);
    }
}
