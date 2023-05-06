package com.example.cargo_transportation.modal.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ContractResponse {
    private Long id;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private CarResponse car;
    private ClientResponse client;
}
