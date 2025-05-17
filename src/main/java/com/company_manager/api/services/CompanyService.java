package com.company_manager.api.services;

import com.company_manager.api.dtos.CompanyDTO;
import com.company_manager.api.exceptions.GenericConflictException;
import com.company_manager.api.exceptions.GenericNotFoundException;
import com.company_manager.api.models.AddressModel;
import com.company_manager.api.models.CompanyModel;
import com.company_manager.api.repositories.CompanyRepository;
import jakarta.persistence.EntityExistsException;
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
        boolean existsByCnpj = companyRepository.existsByCnpj(dto.getCnpj());
        if (existsByCnpj)
            throw new GenericConflictException("Já existe uma empresa com esse CNPJ!");

        boolean existsByCompanyName = companyRepository.existsByCompanyName(dto.getCompanyName());
        if (existsByCompanyName)
            throw new GenericConflictException("Já existe uma empresa com esse nome!");

        boolean existsByFantasyName = companyRepository.existsByFantasyName(dto.getFantasyName());
        if (existsByFantasyName)
            throw new GenericConflictException("Já existe uma empresa com esse nome fantasia!");

        AddressModel address = addressService.findById(dto.getAddressId());

        boolean existsByAddress = companyRepository.existsByAddress(address);
        if (existsByAddress)
            throw new GenericConflictException("Já existe uma empresa com esse endereço!");
        CompanyModel companyModel = new CompanyModel();
        BeanUtils.copyProperties(dto, companyModel);
        companyModel.setAddress(address);
        return companyRepository.save(companyModel);

    }

    public List<CompanyModel> findByPartialFantasyName(String fantasyName) {
        if (fantasyName.isEmpty()) {
            return companyRepository.findAll();
        }
        return companyRepository.findByFantasyNameContainingIgnoreCase(fantasyName);
    }

    public CompanyModel findByCnpj(String cnpj) {
        return companyRepository.findByCnpj(cnpj).orElseThrow(
                () -> new GenericNotFoundException("Empresa não encontrada!")
        );
    }


}
