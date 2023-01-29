package com.example.cargo_transportation.dto;

import com.example.cargo_transportation.entity.Car;
import com.example.cargo_transportation.entity.Contract;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ClientDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    private String email;
    @NotEmpty
    private String INN;
    @NotEmpty
    private String KPP;

    private List<CarDTO> cars = new ArrayList<>();
    private List<ContractDTO> contracts = new ArrayList<>();
}
