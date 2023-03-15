package com.example.cargo_transportation.modal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PriceDTO {
    @NotNull
    private Long serviceId;
    @NotNull
    private Integer cost;

    public PriceDTO() {}

    public PriceDTO(Long id, Integer cost) {
        this.serviceId = id;
        this.cost = cost;
    }
}
