package com.example.cargo_transportation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class JournalDTO {
    private Long id;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @NotNull
    private LocalDateTime incomingDate;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @NotNull
    private LocalDateTime outPlanDate;
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @NotNull
    private LocalDateTime outFactDate;

    private CarDTO car;
    private List<RenderFavorDTO> renderServices = new ArrayList<>();
}