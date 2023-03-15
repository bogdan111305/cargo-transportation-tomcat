package com.example.cargo_transportation.modal.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RefreshToken {
    @NotBlank
    private String token;
}
