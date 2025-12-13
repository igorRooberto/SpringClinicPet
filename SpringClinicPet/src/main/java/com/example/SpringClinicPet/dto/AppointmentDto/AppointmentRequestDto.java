package com.example.SpringClinicPet.dto.AppointmentDto;

import com.example.SpringClinicPet.model.Pet;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.UUID;

public record AppointmentRequestDto(@NotNull LocalDateTime dateTime, @NotBlank String reason, @NotNull UUID petId) {
}
