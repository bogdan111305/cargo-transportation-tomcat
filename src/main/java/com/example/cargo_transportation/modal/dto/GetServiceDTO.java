package com.example.cargo_transportation.modal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetServiceDTO {
    @NotNull
    private Long serviceId;
    @NotNull
    private Integer count;

    public GetServiceDTO() {}

    public GetServiceDTO(Long id, Integer count) {
        this.serviceId = id;
        this.count = count;
    }
}
