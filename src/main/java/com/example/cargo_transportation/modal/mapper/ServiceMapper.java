package com.example.cargo_transportation.modal.mapper;

import com.example.cargo_transportation.entity.Service;
import com.example.cargo_transportation.modal.dto.ServiceRequest;
import com.example.cargo_transportation.modal.dto.ServiceResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ServiceMapper {
    ServiceResponse toDTO(Service service);
    @Mapping(target = "provideServices", ignore = true)
    @Mapping(target = "prices", ignore = true)
    @Mapping(target = "id", ignore = true)
    Service toEntity(ServiceRequest serviceRequest);
}
