package com.example.cargo_transportation.modal.dto;

import com.example.cargo_transportation.entity.enums.JournalStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JournalResponse {
    private Long id;
    private LocalDateTime incomingDate;
    private LocalDateTime outDate;
    private JournalStatus status;
    private String waybill;
    private String nameDriver;
    private CarResponse car;
}
