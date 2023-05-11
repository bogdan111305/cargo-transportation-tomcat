package com.example.cargo_transportation.modal.dto;

import com.example.cargo_transportation.entity.enums.JournalStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class JournalRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @NotNull
    private LocalDateTime incomingDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime outDate;
    private JournalStatus status;
    private String waybill;
    private String nameDriver;
    @NotNull
    private Long carId;
    private List<ProvideServiceRequest> provideServices;
}
