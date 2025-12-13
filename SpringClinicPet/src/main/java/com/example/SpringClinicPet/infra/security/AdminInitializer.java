package com.example.SpringClinicPet.infra.security;

import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.UserRole;
import com.example.SpringClinicPet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminInitializer(PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Value("${api.security.secret.adminLogin}")
    private String adminLogin;

    @Value("${api.security.secret.adminPassword}")
    private String adminPassword;

    @Override
    public void run(String... args) throws Exception {

        if(userRepository.findByLogin(adminLogin) == null){

            User userAdmin = new User(adminLogin,passwordEncoder.encode(adminPassword), UserRole.ADMIN, true,null);
            userRepository.save(userAdmin);
        }

    }
}
