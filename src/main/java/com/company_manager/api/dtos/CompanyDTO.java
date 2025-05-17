package com.company_manager.api.dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyDTO {

    @NotBlank
    @Size(max = 80, min = 5)
    private String companyName;

    @NotBlank
    @Size(max = 80, min = 5)
    private String fantasyName;

    @NotBlank
    @Size(max = 14, min = 14)
    private String cnpj;

    @NotBlank
    @Size(max = 40, min = 10)
    private String email;


    @Size(max = 200)
    private String observation;

    @NotNull
    private Long addressId;

}
