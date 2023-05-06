package com.example.cargo_transportation.modal.mapper;

import com.example.cargo_transportation.entity.Journal;
import com.example.cargo_transportation.modal.dto.JournalRequest;
import com.example.cargo_transportation.modal.dto.JournalResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface JournalMapper {
    @Mapping(target = "car.client", ignore = true)
    JournalResponse toDTO(Journal journal);
    @Mapping(target = "car", ignore = true)
    @Mapping(target = "getServices", ignore = true)
    @Mapping(target = "id", ignore = true)
    Journal toEntity(JournalRequest journalRequest);
}
