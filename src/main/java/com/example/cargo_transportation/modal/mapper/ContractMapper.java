package com.example.cargo_transportation.modal.mapper;

import com.example.cargo_transportation.entity.Contract;
import com.example.cargo_transportation.modal.dto.ContractRequest;
import com.example.cargo_transportation.modal.dto.ContractResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ContractMapper {
    ContractResponse toDTO(Contract contract);
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "client", ignore = true)
    @Mapping(target = "prices", ignore = true)
    Contract toEntity(ContractRequest contractRequest);
}
