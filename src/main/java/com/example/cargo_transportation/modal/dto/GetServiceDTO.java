package com.example.cargo_transportation.modal.dto;

import com.example.cargo_transportation.entity.GetService;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetServiceDTO {
    @NotNull
    private Long serviceId;
    @NotNull
    private Integer count;

    public GetServiceDTO() {}

    public GetServiceDTO(Long id, Integer count) {
        this.serviceId = id;
        this.count = count;
    }

    public GetServiceDTO(GetService getService) {
        this.serviceId = getService.getService().getId();
        this.count = getService.getCount();
    }
}
