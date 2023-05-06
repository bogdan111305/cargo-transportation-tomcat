package com.example.cargo_transportation.modal.mapper;

import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.modal.dto.ClientRequest;
import com.example.cargo_transportation.modal.dto.ClientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ClientMapper {
    ClientResponse toDTO(Client client);
    @Mapping(target = "cars", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    Client toEntity(ClientRequest clientRequest);
}
