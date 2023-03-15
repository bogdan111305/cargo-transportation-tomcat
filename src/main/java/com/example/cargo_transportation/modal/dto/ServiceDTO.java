package com.example.cargo_transportation.modal.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ServiceDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
}
