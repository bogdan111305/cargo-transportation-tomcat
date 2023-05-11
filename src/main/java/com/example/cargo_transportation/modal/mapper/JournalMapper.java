package com.example.cargo_transportation.modal.mapper;

import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.entity.ProvideService;
import com.example.cargo_transportation.modal.dto.JournalRequest;
import com.example.cargo_transportation.modal.dto.JournalResponse;
import com.example.cargo_transportation.modal.dto.ProvideServiceResponse;
import org.mapstruct.*;

@Mapper
public interface JournalMapper {
    @Mapping(target = "car.client", ignore = true)
    JournalResponse toDTO(Journal journal);
    JournalResponse toDTOWithClient(Journal journal);
    @Mappings(
        value = {
            @Mapping(target = "car", ignore = true),
            @Mapping(target = "provideServices", ignore = true),
            @Mapping(target = "id", ignore = true)
    })
    Journal toEntity(JournalRequest journalRequest);
}
