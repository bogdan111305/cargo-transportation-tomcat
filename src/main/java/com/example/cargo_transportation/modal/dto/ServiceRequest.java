package com.example.cargo_transportation.modal.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ServiceRequest {
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
