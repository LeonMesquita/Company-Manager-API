package com.company_manager.api.services;

import com.company_manager.api.dtos.AddressDto;
import com.company_manager.api.exceptions.GenericNotFoundException;
import com.company_manager.api.models.AddressModel;
import com.company_manager.api.repositories.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    final AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressModel> findAll() {
        return addressRepository.findAll();
    }

    public AddressModel findById(Long id) {
        return addressRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException(String.format("Endereço com o id %d não encontrado!", id))
        );
    }


    public AddressModel save(AddressDto dto) {
        AddressModel addressModel = new AddressModel();
        BeanUtils.copyProperties(dto, addressModel);
        return addressRepository.save(addressModel);
    }

    public AddressModel update(Long id, AddressDto dto) {
        UserService.adminOrUserAuthenticated(id);
        AddressModel addressModel = this.findById(id);
        BeanUtils.copyProperties(dto, addressModel, "id");
        return addressRepository.save(addressModel);

    }
}
