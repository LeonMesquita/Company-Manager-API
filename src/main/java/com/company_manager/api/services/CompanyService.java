package com.company_manager.api.services;

import com.company_manager.api.dtos.CompanyDTO;
import com.company_manager.api.dtos.CompanyWithVehiclesResponseDTO;
import com.company_manager.api.dtos.VehicleResponseDTO;
import com.company_manager.api.exceptions.GenericConflictException;
import com.company_manager.api.exceptions.GenericNotFoundException;
import com.company_manager.api.models.AddressModel;
import com.company_manager.api.models.CompanyModel;
import com.company_manager.api.repositories.CompanyRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    AddressService addressService;

    public Page<CompanyModel> findAll(Pageable pageable) {
        return companyRepository.findAll(pageable);
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

    public CompanyModel findById(Long id) {
        return companyRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("Empresa com o id " + id + " não encontrada!")
        );
    }

    public CompanyWithVehiclesResponseDTO getCompanyWithVehicles(Long id) {
        CompanyModel model = companyRepository.findWithVehiclesById(id)
                .orElseThrow(() -> new GenericNotFoundException("Empresa não encontrada"));

        List<VehicleResponseDTO> vehicleDtos = model.getVehicles().stream()
                .map(v -> new VehicleResponseDTO(
                        v.getId(), v.getVehicleDescription(), v.getPlate(), v.getPrice()))
                .collect(Collectors.toList());

        return new CompanyWithVehiclesResponseDTO(
                model.getId(), model.getFantasyName(), model.getCompanyName(),
                model.getCnpj(), model.getEmail(), model.getObservation(),
                vehicleDtos
        );
    }


}
