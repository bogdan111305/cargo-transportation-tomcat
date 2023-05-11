package com.example.cargo_transportation.modal.dto;

import lombok.Data;

@Data
public class PriceResponse {
    private ServiceResponse service;
    private Integer cost;
}
