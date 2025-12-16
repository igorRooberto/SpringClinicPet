package com.example.SpringClinicPet.controller;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.dto.AppointmentDto.DiagnosisNotesRequestDto;
import com.example.SpringClinicPet.services.VeterinarianAppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/veterinarian")
public class VeterinarianAppointmentController {

    private final VeterinarianAppointmentService veterinarianAppointmentService;

    public VeterinarianAppointmentController(VeterinarianAppointmentService veterinarianAppointmentService) {
        this.veterinarianAppointmentService = veterinarianAppointmentService;
    }

    @PatchMapping("/{id}/finalize")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<Void> setDiagnosticsAndStatusCompleted(@PathVariable UUID id,
                                                                 @RequestBody @Valid DiagnosisNotesRequestDto data) {
        veterinarianAppointmentService.setDiagnosticsAndStatusCompleted(id, data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/my-schedule/completed")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByVeterinarianCompleted() {
        return ResponseEntity.ok(veterinarianAppointmentService.getConsultationsByVeterinarianCompleted());
    }

    @GetMapping("/my-schedule/scheduled")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByVeterinarianScheduled() {
        return ResponseEntity.ok(veterinarianAppointmentService.getConsultationsByVeterinarianScheduled());
    }

    @GetMapping("/my-schedule")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByVeterinarian() {
        return ResponseEntity.ok(veterinarianAppointmentService.getConsultationsByVeterinarian());
    }

}
