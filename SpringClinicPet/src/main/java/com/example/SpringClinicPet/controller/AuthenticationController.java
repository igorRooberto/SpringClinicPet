package com.example.SpringClinicPet.controller;

import com.example.SpringClinicPet.dto.AuthenticateDto.AuthenticateDto;
import com.example.SpringClinicPet.dto.AuthenticateDto.LoginResponseDto;
import com.example.SpringClinicPet.dto.AuthenticateDto.RegisterDto;
import com.example.SpringClinicPet.dto.registerVeterinarianDto.RegisterVeterinarianDto;
import com.example.SpringClinicPet.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody AuthenticateDto data){

        LoginResponseDto token = authenticationService.login(data);

        return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto data){
        authenticationService.register(data);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PostMapping("/register/veterinarian")
    public ResponseEntity<Void> registerVeterinarian(@RequestBody @Valid RegisterVeterinarianDto data){
        authenticationService.registerVeterinarian(data);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/register/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> registerAdmin(@RequestBody @Valid RegisterDto data){
        authenticationService.registerAdmin(data);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
