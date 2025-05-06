package com.company_manager.api.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class AddressDto {

    @NotBlank(message = "Street is required")
    @Size(max = 80)
    private String street;

    @Size(max = 50)
    private String complement;

    @NotBlank(message = "Street is required")
    @Size(max = 40)
    private String district;

    @NotBlank(message = "Street is required")
    @Size(max = 10)
    private String number;

    @NotBlank(message = "Street is required")
    @Size(max = 2)
    private String uf;
}
