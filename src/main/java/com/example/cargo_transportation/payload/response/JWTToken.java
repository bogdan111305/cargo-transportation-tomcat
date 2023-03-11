package com.example.cargo_transportation.payload.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTToken {
    private boolean success;
    private String prefix;
    private String accessToken;
    private String refreshToken;
}