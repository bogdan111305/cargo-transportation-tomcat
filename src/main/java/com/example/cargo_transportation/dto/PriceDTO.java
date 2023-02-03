package com.example.cargo_transportation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PriceDTO {
    private Long id;
    @NotNull
    private ServiceDTO service;
    @NotNull
    private ContractDTO contract;
    @NotNull
    private Integer cost;
}
