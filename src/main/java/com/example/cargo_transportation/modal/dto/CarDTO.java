package com.example.cargo_transportation.modal.dto;

import com.example.cargo_transportation.annotations.Unique;
import com.example.cargo_transportation.entity.Car;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Unique(entity = Car.class)
@Data
public class CarDTO {
    private Long id;
    @NotEmpty
    private String gosNum;
    @NotEmpty
    private String sts;
    @NotEmpty
    private String model;

    @NotNull
    private Long clientId;
    private ClientDTO client;
}
