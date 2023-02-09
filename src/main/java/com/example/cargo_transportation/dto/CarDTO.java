package com.example.cargo_transportation.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
}
