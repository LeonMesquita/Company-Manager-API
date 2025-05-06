package com.company_manager.api.services;

import com.company_manager.api.dtos.AddressDto;
import com.company_manager.api.models.AddressModel;
import com.company_manager.api.repositories.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressModel save(AddressDto dto) {
        AddressModel addressModel = new AddressModel();
        BeanUtils.copyProperties(dto, addressModel);
        return addressRepository.save(addressModel);
    }
}
