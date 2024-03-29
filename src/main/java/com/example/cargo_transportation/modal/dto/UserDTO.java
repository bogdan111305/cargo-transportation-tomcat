package com.example.cargo_transportation.modal.dto;

import com.example.cargo_transportation.annotations.Unique;
import com.example.cargo_transportation.entity.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Unique(entity = User.class)
@Data
public class UserDTO {
    private Long id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String firstname;
    @NotEmpty
    private String lastname;
}