package com.example.SpringClinicPet.dto.AppointmentDto;

import com.example.SpringClinicPet.model.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentResponseDto(UUID id,
                                     LocalDateTime dateTime,
                                     String reason,
                                     AppointmentStatus status,
                                     String diagnosisNotes,
                                     String veterinarian) {
}
