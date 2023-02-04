package com.example.cargo_transportation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractDTO {
    private Long id;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @NotNull
    private LocalDateTime startDate;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @NotNull
    private LocalDateTime endDate;
    private boolean defaultPrice;

    private Long carId;
    private Long clientId;

    private CarDTO car;
    private ClientDTO client;
}
