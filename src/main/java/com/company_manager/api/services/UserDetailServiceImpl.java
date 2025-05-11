package com.company_manager.api.services;

import com.company_manager.api.exceptions.GenericNotFoundException;
import com.company_manager.api.models.UserModel;
import com.company_manager.api.repositories.UserRepository;
import com.company_manager.api.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws GenericNotFoundException {
        UserModel user = this.userRepository.findByUsername(username);
        if(Objects.isNull(user)) {
            throw new GenericNotFoundException("Usuário não encontrado: " + username);
        }
        return new UserSpringSecurity(user.getId(), user.getUsername(), user.getPassword(), user.getProfiles());
    }
}
