package com.company_manager.api.services;

import com.company_manager.api.dtos.UserDTO;
import com.company_manager.api.models.UserModel;
import com.company_manager.api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserModel save(UserDTO dto) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);
        return userRepository.save(userModel);

    }
}
