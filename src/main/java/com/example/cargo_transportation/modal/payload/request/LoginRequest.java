package com.example.cargo_transportation.modal.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {

    @NotEmpty(message = "Username not can empty")
    private String username;
    @NotEmpty(message = "Password not can empty")
    private String password;
}
