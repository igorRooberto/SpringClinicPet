package com.example.SpringClinicPet.services;

import com.example.SpringClinicPet.dto.AuthenticateDto.AuthenticateDto;
import com.example.SpringClinicPet.dto.AuthenticateDto.LoginResponseDto;
import com.example.SpringClinicPet.dto.AuthenticateDto.RegisterDto;
import com.example.SpringClinicPet.dto.registerVeterinarianDto.RegisterVeterinarianDto;
import com.example.SpringClinicPet.infra.security.TokenService;
import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.UserRole;
import com.example.SpringClinicPet.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public AuthenticationService(AuthenticationManager authenticationManager
                                , UserRepository userRepository
                                , PasswordEncoder passwordEncoder
                                , TokenService tokenService) {

        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
    }

    public void register(RegisterDto data) {
        if(this.userRepository.findByLogin(data.login()) != null){
            throw new RuntimeException("Username already exists");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.login(),encryptedPassword,UserRole.USER, true, null);

        userRepository.save(newUser);
    }

    public void registerVeterinarian(RegisterVeterinarianDto data){
        if(this.userRepository.findByLogin(data.login()) != null){
            throw new RuntimeException("Username already exists");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.login(),encryptedPassword,UserRole.VETERINARIAN, false, data.crvm());

        userRepository.save(newUser);
    }

    public void registerAdmin(RegisterDto data){
        if(this.userRepository.findByLogin(data.login()) != null){
            throw new RuntimeException("Username already exists");
        }

        String encryptedPassword = passwordEncoder.encode(data.password());
        User newUser = new User(data.login(),encryptedPassword,UserRole.ADMIN, true, null);

        userRepository.save(newUser);
    }

    public LoginResponseDto login(AuthenticateDto data){
        var usernamePassword =  new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var authentication = authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) authentication.getPrincipal());

        return new LoginResponseDto(token);
    }

}
