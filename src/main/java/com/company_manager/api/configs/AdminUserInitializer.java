package com.company_manager.api.configs;

import com.company_manager.api.models.UserModel;
import com.company_manager.api.models.enums.ProfileEnum;
import com.company_manager.api.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AdminUserInitializer {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    public void initAdminUser() {
        String defaultAdminUsername = "admin";

        if (!userRepository.existsByUsername(defaultAdminUsername)) {
            UserModel admin = new UserModel();
            admin.setUsername(defaultAdminUsername);
            admin.setPassword(bCryptPasswordEncoder.encode("new@admin")); // escolha uma senha segura
            admin.setProfiles(Stream.of(ProfileEnum.ADMIN.getCode()).collect(Collectors.toSet()));
            userRepository.save(admin);

            System.out.println("Usuário ADMIN padrão criado: admin / admin123");
        }
    }
}
