package com.example.cargo_transportation.dto;

import com.example.cargo_transportation.entity.Client;
import com.example.cargo_transportation.entity.Contract;
import com.example.cargo_transportation.entity.Journal;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class CarDTO {
    private Long id;
    @NotEmpty
    private String gosNum;
    @NotEmpty
    private String STS;
    @NotEmpty
    private String model;

    private Long clientId;

    private ClientDTO client;
    private List<ContractDTO> contracts;
    private List<JournalDTO> journals;
}
