package com.example.SpringClinicPet.services;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.dto.AppointmentDto.DiagnosisNotesRequestDto;
import com.example.SpringClinicPet.infra.security.UserSecurityHelper;
import com.example.SpringClinicPet.mapper.AppointmentMapper;
import com.example.SpringClinicPet.model.Appointment;
import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.AppointmentStatus;
import com.example.SpringClinicPet.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VeterinarianAppointmentService {

    private final UserSecurityHelper userSecurityHelper;
    private final AppointmentRepository appointmentRepository;
    private final AppointmentMapper appointmentMapper;

    public VeterinarianAppointmentService(AppointmentMapper appointmentMapper, UserSecurityHelper userSecurityHelper, AppointmentRepository appointmentRepository) {
        this.appointmentMapper = appointmentMapper;
        this.userSecurityHelper = userSecurityHelper;
        this.appointmentRepository = appointmentRepository;
    }

    public void setDiagnosticsAndStatusCompleted(UUID appointmentId, DiagnosisNotesRequestDto data){
        User veterinarian = userSecurityHelper.getAuthenticatedUser();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Erro consulta Não encontrada"));

        if(!(appointment.belongsVeterinarian(veterinarian))){
            throw new RuntimeException("Você Não tem acesso a essa consulta");
        }

        appointment.finalizedConsultation(data.diagnosisNotes());

        appointmentRepository.save(appointment);
    }

    public List<AppointmentResponseDto> getConsultationsByVeterinarian(){
        User veterinarian = userSecurityHelper.getAuthenticatedUser();

        List<Appointment> appointmentList = appointmentRepository.findByVeterinarian(veterinarian);

        return appointmentList.stream().map(appointmentMapper::toResponseDto).collect(Collectors.toList());
    }



    public List<AppointmentResponseDto> getConsultationsByVeterinarianCompleted(){
        User veterinarian = userSecurityHelper.getAuthenticatedUser();

        List<Appointment> appointmentList = appointmentRepository.findByVeterinarian(veterinarian);

        return appointmentList.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.COMPLETED)
                .map(appointmentMapper::toResponseDto).toList();

    }

    public List<AppointmentResponseDto> getConsultationsByVeterinarianScheduled(){
        User veterinarian = userSecurityHelper.getAuthenticatedUser();

        List<Appointment> appointmentList = appointmentRepository.findByVeterinarian(veterinarian);

        return appointmentList.stream()
                .filter(appointment -> appointment.getStatus() == AppointmentStatus.SCHEDULED)
                .map(appointmentMapper::toResponseDto).toList();

    }

}
