package com.company_manager.api.services;

import com.company_manager.api.dtos.UserDTO;
import com.company_manager.api.exceptions.AuthorizationException;
import com.company_manager.api.exceptions.GenericConflictException;
import com.company_manager.api.exceptions.GenericNotFoundException;
import com.company_manager.api.models.UserModel;
import com.company_manager.api.models.enums.ProfileEnum;
import com.company_manager.api.repositories.UserRepository;
import com.company_manager.api.security.UserSpringSecurity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel save(UserDTO dto) {
        boolean usernameExists = userRepository.existsByUsername(dto.getUsername());
        if (usernameExists) {
            throw new GenericConflictException("O usuário " + dto.getUsername() + " já existe!");
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(dto, userModel);
        userModel.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        // Garante que o usuário vai ser criado com o ROLE de usuário
        userModel.setProfiles(Stream.of(ProfileEnum.USER.getCode()).collect(Collectors.toSet()));
        return userRepository.save(userModel);
    }




    public UserModel update(Long id, UserDTO dto) {
        boolean usernameExists = userRepository.existsByUsername(dto.getUsername());
        if (usernameExists) {
            throw new GenericConflictException("O usuário " + dto.getUsername() + " já existe!");
        }
        UserModel userModel = this.findById(id);
        BeanUtils.copyProperties(dto, userModel, "id");
        userModel.setPassword(this.bCryptPasswordEncoder.encode(dto.getPassword()));
        return userRepository.save(userModel);
    }


    public void delete(Long id) {
        adminOrUserAuthenticated(id);
        UserModel user = userRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("Usuário não encontrado!")
        );

        userRepository.delete(user);
    }

    public UserModel findById(Long id) {
        adminOrUserAuthenticated(id);
        return userRepository.findById(id).orElseThrow(
                () -> new GenericNotFoundException("Usuário não encontrado!")
        );
    }

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public static void adminOrUserAuthenticated(Long id) {
        try {
            UserSpringSecurity userSpringSecurity = (UserSpringSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (!Objects.nonNull(userSpringSecurity) || (!userSpringSecurity.hasRole(ProfileEnum.ADMIN)  && !id.equals(userSpringSecurity.getId())))
                throw new AuthorizationException("Acesso negado!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
