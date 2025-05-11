package com.company_manager.api.services;

import com.company_manager.api.dtos.UserDTO;
import com.company_manager.api.models.UserModel;
import com.company_manager.api.models.enums.ProfileEnum;
import com.company_manager.api.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel save(UserDTO dto) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);
        userModel.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        // Garante que o usuário vai ser criado com o ROLE de usuário
        userModel.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        return userRepository.save(userModel);
    }

    public UserModel update(UserDTO dto, Long id) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);
        userModel.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        return userRepository.save(userModel);
    }
}
