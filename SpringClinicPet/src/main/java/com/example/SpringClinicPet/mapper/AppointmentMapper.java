package com.example.SpringClinicPet.mapper;


import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.model.Appointment;
import com.example.SpringClinicPet.model.User;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

     public AppointmentResponseDto toResponseDto(Appointment appointment) {
        return new AppointmentResponseDto(
                appointment.getId(),
                appointment.getDateTime(),
                appointment.getReason(),
                appointment.getStatus(),
                formatDiagnosis(appointment.getDiagnosisNotes()),
                formatVeterinarianName(appointment.getVeterinarian())
        );
    }

    private String formatDiagnosis(String notes) {
        if (notes != null && !notes.isBlank()) {
            return notes;
        }
        return "Diagnóstico a Definir no Dia Da Consulta";
    }

    private String formatVeterinarianName(User veterinarian) {
        if (veterinarian != null) {
            return veterinarian.getLogin();
        }
        return "Veterinário A Definir";
    }

}
