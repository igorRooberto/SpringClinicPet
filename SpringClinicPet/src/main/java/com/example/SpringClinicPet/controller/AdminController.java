package com.example.SpringClinicPet.controller;

import com.example.SpringClinicPet.dto.registerVeterinarianDto.VeterinarianResponseDto;
import com.example.SpringClinicPet.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PatchMapping("/activate/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> changeStatusForActivate(@PathVariable UUID userId){

        adminService.changeUserStatusActivate(userId);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/deactivate/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> changeStatusForDeactivate(@PathVariable UUID userId){

        adminService.changeUserStatusDeactivate(userId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/pending/veterinarians")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VeterinarianResponseDto>> getAllPendingVeterinarians(){

        List<VeterinarianResponseDto> list = adminService.findAllPendingVeterinarians();

        return ResponseEntity.ok(list);
    }

    @GetMapping("/activate/veterinarians")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<VeterinarianResponseDto>> getAllActivateVeterinarians(){
        List<VeterinarianResponseDto> list = adminService.findAllVeterinariansActivate();

        return ResponseEntity.ok(list);
    }
}
