package com.company_manager.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResponseDTO {
    private Long id;
    private String fantasyName;
    private String companyName;
    private String cnpj;
    private String email;
    private String observation;
}
