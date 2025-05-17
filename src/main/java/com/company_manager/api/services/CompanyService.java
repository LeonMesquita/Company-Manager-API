package com.company_manager.api.services;

import com.company_manager.api.dtos.CompanyDTO;
import com.company_manager.api.models.AddressModel;
import com.company_manager.api.models.CompanyModel;
import com.company_manager.api.repositories.CompanyRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressService addressService;

    public List<CompanyModel> findAll() {
        return companyRepository.findAll();
    }

    public CompanyModel save(CompanyDTO dto) {
        AddressModel address = addressService.findById(dto.getAddressId());
        CompanyModel companyModel = new CompanyModel();
        BeanUtils.copyProperties(dto, companyModel);
        companyModel.setAddress(address);
        return companyRepository.save(companyModel);

    }

    public List<CompanyModel> findByPartialCnpj(String cnpj) {
        return companyRepository.findByCnpjContaining(cnpj);
    }


}
