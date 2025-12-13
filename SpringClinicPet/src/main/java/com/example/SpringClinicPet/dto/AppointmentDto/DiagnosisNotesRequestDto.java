package com.example.SpringClinicPet.dto.AppointmentDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DiagnosisNotesRequestDto(@NotBlank(message = "O diagnóstico Não pode estar vazio")
                                       @Size(max = 2000, message = "O limite é de 2000 Caracteres")
                                       String diagnosisNotes) {
}
