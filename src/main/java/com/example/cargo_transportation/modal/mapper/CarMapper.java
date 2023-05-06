package com.example.cargo_transportation.modal.mapper;

import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.modal.dto.CarRequest;
import com.example.cargo_transportation.modal.dto.CarResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CarMapper {
    CarResponse toDTO(Car car);
    @Mapping(target = "client.cars", ignore = true)
    @Mapping(target = "client.contracts", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    @Mapping(target = "journals", ignore = true)
    @Mapping(target = "id", ignore = true)
    Car toEntity(CarRequest carRequest);
}
