package com.company_manager.api.services;

import com.company_manager.api.dtos.VehicleDTO;
import com.company_manager.api.exceptions.GenericConflictException;
import com.company_manager.api.exceptions.GenericNotFoundException;
import com.company_manager.api.models.CompanyModel;
import com.company_manager.api.models.VehicleModel;
import com.company_manager.api.repositories.VehicleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public VehicleModel findById(Long id) {
        return vehicleRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("Veículo com o id " + id + " não encontrado!")
        );
    }

    public VehicleModel update(Long id, VehicleDTO dto) {
        VehicleModel vehicleModel = this.findById(id);
        Optional<VehicleModel> existingVehicle = vehicleRepository.findByPlate(dto.getPlate());
        if (existingVehicle.isPresent() && !Objects.equals(existingVehicle.get().getPlate(), vehicleModel.getPlate())) {
            throw new GenericConflictException("já existe um veículo com essa placa");
        }
        BeanUtils.copyProperties(dto, vehicleModel, "id");
        return vehicleRepository.save(vehicleModel);
    }

    public void delete(Long id) {
        VehicleModel vehicle = this.findById(id);
        vehicleRepository.delete(vehicle);
    }
}
