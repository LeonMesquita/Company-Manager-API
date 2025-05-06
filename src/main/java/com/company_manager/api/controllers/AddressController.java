package com.company_manager.api.controllers;

import com.company_manager.api.dtos.AddressDto;
import com.company_manager.api.models.AddressModel;
import com.company_manager.api.services.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    final AddressService addressService;
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    @PostMapping
    public ResponseEntity<AddressModel> createAddress(@RequestBody @Valid AddressDto body) {
        AddressModel address = addressService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }
}
