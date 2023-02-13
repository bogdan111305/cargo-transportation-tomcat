package com.example.cargo_transportation.dto;

import com.example.cargo_transportation.annotations.Unique;
import com.example.cargo_transportation.annotations.UniqueColumn;
import com.example.cargo_transportation.entity.Client;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Unique(entity = Client.class,
        columns = @UniqueColumn(fields = {"name", "email", "inn"}))
@Data
public class ClientDTO {
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String address;
    @NotEmpty
    private String email;
    @NotEmpty
    private String inn;
    @NotEmpty
    private String kpp;
    private String rs;
    private String bank;
    private String bik;
    private String ks;
}
