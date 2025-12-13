package com.example.SpringClinicPet.controller;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentRequestDto;
import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.dto.AppointmentDto.DiagnosisNotesRequestDto;
import com.example.SpringClinicPet.services.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> scheduleConsultation(@RequestBody @Valid AppointmentRequestDto data){
        AppointmentResponseDto responseDto = appointmentService.scheduleConsultation(data);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/cancel/{appointmentId}")
    public ResponseEntity<Void> cancelConsultation(UUID appointmentId){
        appointmentService.cancelConsultation(appointmentId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/define/veterinarian/{veterinarianId}/{appointmentId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> setVeterinarianAvailable(@PathVariable UUID veterinarianId, @PathVariable UUID appointmentId){
        appointmentService.setVeterinarianAvailable(veterinarianId,appointmentId);

        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{id}/finalize")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<Void> setDiagnosticsAndStatusCompleted(@PathVariable UUID id,
                                                                 @RequestBody @Valid DiagnosisNotesRequestDto data) {
        appointmentService.setDiagnosticsAndStatusCompleted(id, data);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AppointmentResponseDto> verifyConsultation(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentService.verifyConsultation(id));
    }

    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByPet(@PathVariable UUID petId) {
        return ResponseEntity.ok(appointmentService.getConsultationsByPet(petId));
    }

    @GetMapping("/available")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsWithoutVeterinarian() {
        return ResponseEntity.ok(appointmentService.getConsultationsWithoutVeterinarian());
    }

    @GetMapping("/my-schedule")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByVeterinarian() {
        return ResponseEntity.ok(appointmentService.getConsultationsByVeterinarian());
    }

    @GetMapping("/my-schedule/completed")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByVeterinarianCompleted() {
        return ResponseEntity.ok(appointmentService.getConsultationsByVeterinarianCompleted());
    }

    @GetMapping("/my-schedule/scheduled")
    @PreAuthorize("hasRole('VETERINARIAN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByVeterinarianScheduled() {
        return ResponseEntity.ok(appointmentService.getConsultationsByVeterinarianScheduled());
    }

    @GetMapping("/veterinarian/{veterinarianId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByVeterinarianAsAdmin(@PathVariable UUID veterinarianId) {
        return ResponseEntity.ok(appointmentService.getConsultationsByVeterinarianAsAdmin(veterinarianId));
    }
}
