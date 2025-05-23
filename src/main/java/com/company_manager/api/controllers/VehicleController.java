package com.company_manager.api.controllers;
import com.company_manager.api.dtos.VehicleDTO;
import com.company_manager.api.models.VehicleModel;
import com.company_manager.api.services.VehicleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<VehicleModel> createVehicle(@RequestBody @Valid VehicleDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(vehicleService.save(body));
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<VehicleModel> getVehicleById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<VehicleModel> updateVehicle(@PathVariable Long id, @RequestBody @Valid VehicleDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(vehicleService.update(id, body));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable Long id) {
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
