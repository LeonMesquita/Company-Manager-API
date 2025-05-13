package com.company_manager.api.controllers;

import com.company_manager.api.dtos.AddressDto;
import com.company_manager.api.models.AddressModel;
import com.company_manager.api.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping
    public ResponseEntity<List<AddressModel>> getAddresses() {
        List<AddressModel> addresses = addressService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AddressModel> createAddress(@RequestBody @Valid AddressDto body) {
        AddressModel address = addressService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }
}
