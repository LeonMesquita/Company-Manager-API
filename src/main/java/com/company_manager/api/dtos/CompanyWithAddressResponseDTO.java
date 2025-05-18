package com.company_manager.api.dtos;

import com.company_manager.api.models.AddressModel;

public class CompanyWithAddressResponseDTO {
    private Long id;
    private String fantasyName;
    private String companyName;
    private String cnpj;
    private String email;
    private String observation;

    private AddressModel address;

}
