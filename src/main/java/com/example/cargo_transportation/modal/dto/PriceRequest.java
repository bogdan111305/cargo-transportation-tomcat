package com.example.cargo_transportation.modal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PriceRequest {
    @NotNull
    private Long serviceId;
    @NotNull
    private Integer cost;
}
