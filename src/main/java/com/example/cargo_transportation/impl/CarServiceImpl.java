package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.modal.dto.CarRequest;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.modal.dto.CarResponse;
import com.example.cargo_transportation.modal.mapper.CarMapper;
import com.example.cargo_transportation.repo.CarRepository;
import com.example.cargo_transportation.service.CarService;
import com.example.cargo_transportation.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ClientService clientService;
    private final CarMapper carMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ClientService clientService, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.clientService = clientService;
        this.carMapper = carMapper;
    }

    @Override
    public CarResponse getCarById(Long carId) {
        return carMapper.toDTO(findCarById(carId));
    }

    @Override
    public CarResponse getCarBySts(String sts) {
        Car car = carRepository.findCarBySts(sts)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with sts: " + sts));
        return carMapper.toDTO(car);
    }

    @Override
    public CarResponse getCarByGosNum(String gosNum) {
        Car car = carRepository.findCarByGosNum(gosNum)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with gosNum: " + gosNum));
        return carMapper.toDTO(car);
    }

    @Override
    public List<CarResponse> getAllCar() {
        List<Car> cars = carRepository.findAll();

        return cars.stream()
                .map(carMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CarResponse createCar(CarRequest carRequest) {
        Client client = clientService.findClientById(carRequest.getClientId());

        Car car = carMapper.toEntity(carRequest);
        car.setClient(client);

        car = carRepository.save(car);
        log.info("The car: {} is created", car.getGosNum());

        return carMapper.toDTO(car);
    }

    @Override
    public CarResponse updateCar(CarRequest carRequest, Long carId) {
        Car car = findCarById(carId);

        car.setGosNum(carRequest.getGosNum());
        car.setSts(carRequest.getSts());
        car.setModel(carRequest.getModel());

        if (!car.getClient().getId().equals(carRequest.getClientId())) {
            Client client = clientService.findClientById(carRequest.getClientId());
            car.setClient(client);
        }

        car = carRepository.save(car);
        log.info("The car: {} is updated", car.getGosNum());

        return carMapper.toDTO(car);
    }

    @Override
    public void deleteCar(Long carId) {
        Car car = findCarById(carId);

        carRepository.delete(car);
        log.info("The car: {} is deleted", car.getGosNum());
    }

    @Override
    public Car findCarById(Long carId) {
        return carRepository.findCarById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));
    }
}
