package com.company_manager.api.services;

import com.company_manager.api.models.VehicleModel;
import com.company_manager.api.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;

    public List<VehicleModel> findAll() {
        return vehicleRepository.findAll();
    }
}
