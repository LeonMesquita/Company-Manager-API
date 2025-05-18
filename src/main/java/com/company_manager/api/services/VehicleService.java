package com.company_manager.api.services;

import com.company_manager.api.dtos.VehicleDTO;
import com.company_manager.api.exceptions.GenericConflictException;
import com.company_manager.api.models.CompanyModel;
import com.company_manager.api.models.VehicleModel;
import com.company_manager.api.repositories.VehicleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    CompanyService companyService;

    public List<VehicleModel> findAll() {
        return vehicleRepository.findAll();
    }

    public VehicleModel save(VehicleDTO dto) {
        boolean existsByPlate = vehicleRepository.existsByPlate(dto.getPlate());
        if (existsByPlate)
            throw new GenericConflictException("já existe um veículo com essa placa");

        CompanyModel companyModel = companyService.findById(dto.getCompanyId());
        VehicleModel vehicleModel = new VehicleModel();
        BeanUtils.copyProperties(dto, vehicleModel);
        vehicleModel.setCompany(companyModel);
        return vehicleRepository.save(vehicleModel);

    }
}
