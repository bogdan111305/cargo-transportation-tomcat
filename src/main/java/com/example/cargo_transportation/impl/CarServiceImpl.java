package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.CarDTO;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.exception.EntityNotFoundException;
import com.example.cargo_transportation.exception.EntityConversionException;
import com.example.cargo_transportation.repo.CarRepository;
import com.example.cargo_transportation.service.CarService;
import com.example.cargo_transportation.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ClientService clientService, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CarDTO> getAllCar() {
        return carRepository.findAll().stream()
                .map(car -> modelMapper.map(car, CarDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarDTO> getCarsByIds(List<Long> ids) {
        return carRepository.findAllById(ids).stream()
                .map(car -> modelMapper.map(car, CarDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Car getCarById(Long carId) {
        return carRepository.findCarById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Client client = clientService.getClientById(carDTO.getClientId());

        Car car = modelMapper.map(carDTO, Car.class);
        car.setClient(client);

        car = carRepository.save(car);
        log.info("The car: {} is created" + car.getGosNum());

        return modelMapper.map(car, CarDTO.class);
    }

    @Override
    public CarDTO updateCar(CarDTO carDTO) {
        Car car = getCarById(carDTO.getClientId());

        car.setGosNum(carDTO.getGosNum());
        car.setSTS(carDTO.getSTS());
        car.setModel(carDTO.getModel());

        if (!car.getClient().getId().equals(carDTO.getClientId())) {
            Client client = clientService.getClientById(carDTO.getClientId());
            car.setClient(client);
        }

        car = carRepository.save(car);
        log.info("The car: {} is updated" + car.getGosNum());

        return modelMapper.map(car, CarDTO.class);
    }

    @Override
    public void deleteCar(Long carId) {
        Car car = getCarById(carId);

        carRepository.delete(car);
        log.info("The car: {} is deleted" + car.getGosNum());
    }
}