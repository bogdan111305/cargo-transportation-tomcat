package com.example.cargo_transportation.modal.mapper;

import com.example.cargo_transportation.entity.Contract;
import com.example.cargo_transportation.modal.dto.ContractDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ContractMapper {
    @Mapping(target = "car.clientId", source = "car.client.id")
    @Mapping(target = "car.client", ignore = true)
    @Mapping(target = "carId", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    ContractDTO toDTO(Contract contract);
}
