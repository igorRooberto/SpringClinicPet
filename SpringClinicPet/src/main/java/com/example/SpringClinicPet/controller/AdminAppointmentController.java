package com.example.SpringClinicPet.controller;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.services.AdminAppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/appointment")
public class AdminAppointmentController {

    private final AdminAppointmentService adminAppointmentService;

    public AdminAppointmentController(AdminAppointmentService adminAppointmentService) {
        this.adminAppointmentService = adminAppointmentService;
    }

    @PatchMapping("/define/veterinarian/{veterinarianId}/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> setVeterinarianAvailable(@PathVariable UUID veterinarianId, @PathVariable UUID appointmentId){
        adminAppointmentService.setVeterinarianAvailable(veterinarianId,appointmentId);

        return ResponseEntity.ok().build();

    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsWithoutVeterinarian() {
        return ResponseEntity.ok(adminAppointmentService.getConsultationsWithoutVeterinarian());
    }

    @GetMapping("/veterinarian/{veterinarianId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByVeterinarianAsAdmin(@PathVariable UUID veterinarianId) {
        return ResponseEntity.ok(adminAppointmentService.getConsultationsByVeterinarianAsAdmin(veterinarianId));
    }

}
