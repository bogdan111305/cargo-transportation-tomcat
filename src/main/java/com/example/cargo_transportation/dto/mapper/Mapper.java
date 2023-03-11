package com.example.cargo_transportation.dto.mapper;

import java.lang.Class;

public interface Mapper {
    <D> D defaultMap(Object source, Class<D> destinationType);

    <D> D mapToDTOWithSpecificFields(Object source, Class<D> destinationType);
}