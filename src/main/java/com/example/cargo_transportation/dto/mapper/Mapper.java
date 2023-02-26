package com.example.cargo_transportation.dto.mapper;

import java.lang.Class;

public interface Mapper {

    <D> D map(Object source, Class<D> destinationType);

    <D> D mapWithSpecificFields(Object source, Class<D> destinationType);

}