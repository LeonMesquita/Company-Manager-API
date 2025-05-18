package com.company_manager.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleResponseDTO {
    private Long id;
    private String vehicleDescription;
    private String plate;
    private Double price;
}
