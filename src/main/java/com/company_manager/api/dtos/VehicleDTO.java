package com.company_manager.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VehicleDTO {
    @NotBlank
    @Size(max = 35)
    private String vehicleDescription;

    @NotBlank
    @Size(max = 7, min = 7)
    private String plate;

    @NotNull
    private Double price;

    @NotNull
    private Integer companyId;

}
