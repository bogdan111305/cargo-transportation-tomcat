package com.example.cargo_transportation.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FavorDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;

    private List<RenderFavorDTO> renderFavors = new ArrayList<>();
    private List<PriceDTO> prices = new ArrayList<>();
}
