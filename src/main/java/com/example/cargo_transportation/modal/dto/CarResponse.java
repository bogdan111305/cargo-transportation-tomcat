package com.example.cargo_transportation.modal.dto;

import lombok.Data;

@Data
public class CarResponse {
    private Long id;
    private String gosNum;
    private String sts;
    private String model;
    private ClientResponse client;
}
