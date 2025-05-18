package com.company_manager.api.controllers;

import com.company_manager.api.models.VehicleModel;
import com.company_manager.api.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    @Autowired
    VehicleService vehicleService;

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<VehicleModel>> getAllVehicles() {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findAll());
    }
}
