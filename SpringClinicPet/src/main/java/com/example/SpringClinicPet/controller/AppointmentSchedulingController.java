package com.example.SpringClinicPet.controller;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentRequestDto;
import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.services.AppointmentSchedulingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/scheduling")
public class AppointmentSchedulingController {

    private final AppointmentSchedulingService appointmentSchedulingService;

    public AppointmentSchedulingController(AppointmentSchedulingService appointmentSchedulingService) {
        this.appointmentSchedulingService = appointmentSchedulingService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDto> scheduleConsultation(@RequestBody @Valid AppointmentRequestDto data){
        AppointmentResponseDto responseDto = appointmentSchedulingService.scheduleConsultation(data);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PatchMapping("/cancel/{appointmentId}")
    public ResponseEntity<Void> cancelConsultation(UUID appointmentId){
        appointmentSchedulingService.cancelConsultation(appointmentId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AppointmentResponseDto> verifyConsultation(@PathVariable UUID id) {
        return ResponseEntity.ok(appointmentSchedulingService.verifyConsultation(id));
    }

    @GetMapping("/pet/{petId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<AppointmentResponseDto>> getConsultationsByPet(@PathVariable UUID petId) {
        return ResponseEntity.ok(appointmentSchedulingService.getConsultationsByPet(petId));
    }
}
