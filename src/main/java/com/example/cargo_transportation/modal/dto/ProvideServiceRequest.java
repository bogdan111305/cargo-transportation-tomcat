package com.example.cargo_transportation.modal.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProvideServiceRequest {
    @NotNull
    private Long serviceId;
    @NotNull
    private Integer count;
}
