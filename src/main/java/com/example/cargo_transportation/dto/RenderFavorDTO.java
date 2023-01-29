package com.example.cargo_transportation.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RenderFavorDTO {
    private Long id;
    @NotNull
    private FavorDTO service;
    @NotNull
    private JournalDTO journal;
    @NotNull
    private Integer count;
}
