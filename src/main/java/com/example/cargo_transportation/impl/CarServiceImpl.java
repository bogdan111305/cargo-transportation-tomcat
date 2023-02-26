package com.example.cargo_transportation.impl;

import com.example.cargo_transportation.dto.CarDTO;
import com.example.cargo_transportation.dto.mapper.CustomMapper;
import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.exception.EntityNotFoundException;
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
    private final CustomMapper customMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ClientService clientService, CustomMapper customMapper) {
        this.carRepository = carRepository;
        this.clientService = clientService;
        this.customMapper = customMapper;
    }

    @Override
    public List<CarDTO> getAllCar(List<Long> ids) {
        List<Car> cars = null;
        if (ids != null && !ids.isEmpty())
            cars = carRepository.findAllById(ids);
        else
            cars = carRepository.findAll();

        return cars.stream()
                .map(car -> customMapper.mapWithSpecificFields(car, CarDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CarDTO getCarBySts(String sts) {
        Car car = carRepository.findCarBySts(sts)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with sts: " + sts));
        return customMapper.mapWithSpecificFields(car, CarDTO.class);
    }

    @Override
    public CarDTO getCarByGosNum(String gosNum) {
        Car car = carRepository.findCarByGosNum(gosNum)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with gosNum: " + gosNum));
        return customMapper.mapWithSpecificFields(car, CarDTO.class);
    }

    @Override
    public CarDTO getCarById(Long carId) {
        return customMapper.mapWithSpecificFields(getCarById(carId), CarDTO.class);
    }

    @Override
    public Car findCarById(Long carId) {
        return carRepository.findCarById(carId)
                .orElseThrow(() -> new EntityNotFoundException("Car not found with id: " + carId));
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        Client client = clientService.findClientById(carDTO.getClientId());

        Car car = customMapper.map(carDTO, Car.class);
        car.setClient(client);

        car = carRepository.save(car);
        log.info("The car: {} is created", car.getGosNum());

        return customMapper.mapWithSpecificFields(car, CarDTO.class);
    }

    @Override
    public CarDTO updateCar(CarDTO carDTO, Long carId) {
        Car car = findCarById(carId);

        car.setGosNum(carDTO.getGosNum());
        car.setSts(carDTO.getSts());
        car.setModel(carDTO.getModel());

        if (!car.getClient().getId().equals(carDTO.getClientId())) {
            Client client = clientService.findClientById(carDTO.getClientId());
            car.setClient(client);
        }

        car = carRepository.save(car);
        log.info("The car: {} is updated", car.getGosNum());

        return customMapper.mapWithSpecificFields(car, CarDTO.class);
    }

    @Override
    public void deleteCar(Long carId) {
        Car car = findCarById(carId);

        carRepository.delete(car);
        log.info("The car: {} is deleted", car.getGosNum());
    }
}
