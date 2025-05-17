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


    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping
    public ResponseEntity<List<AddressModel>> getAddresses() {
        List<AddressModel> addresses = addressService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(addresses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressModel> getAddressById(@PathVariable Long id) {
        AddressModel address = addressService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<AddressModel> createAddress(@RequestBody @Valid AddressDto body) {
        AddressModel address = addressService.save(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<AddressModel> updateAddress(@PathVariable Long id, @RequestBody @Valid AddressDto body) {
        AddressModel address = addressService.update(id, body);
        return ResponseEntity.status(HttpStatus.OK).body(address);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
