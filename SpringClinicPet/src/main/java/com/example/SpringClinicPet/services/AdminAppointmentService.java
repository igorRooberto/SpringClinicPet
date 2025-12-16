package com.example.SpringClinicPet.services;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.infra.security.UserSecurityHelper;
import com.example.SpringClinicPet.mapper.AppointmentMapper;
import com.example.SpringClinicPet.model.Appointment;
import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.UserRole;
import com.example.SpringClinicPet.repository.AppointmentRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserSecurityHelper userSecurityHelper;
    private final AppointmentMapper appointmentMapper;
    private final AdminService adminService;

    public AdminAppointmentService(AdminService adminService,
                                   AppointmentRepository appointmentRepository,
                                   UserSecurityHelper userSecurityHelper,
                                   AppointmentMapper appointmentMapper) {

        this.adminService = adminService;
        this.appointmentRepository = appointmentRepository;
        this.userSecurityHelper = userSecurityHelper;
        this.appointmentMapper = appointmentMapper;
    }

    public List<AppointmentResponseDto> getConsultationsByVeterinarianAsAdmin(UUID veterinarianIdTarget) {
        User currentUser = userSecurityHelper.getAuthenticatedUser();

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new AccessDeniedException("Apenas administradores podem realizar esta busca.");
        }

        User targetVeterinarian = adminService.getById(veterinarianIdTarget);

        return appointmentRepository.findByVeterinarian(targetVeterinarian)
                .stream()
                .map(appointmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    public List<AppointmentResponseDto> getConsultationsWithoutVeterinarian(){
        List<Appointment> consultationsWithoutVeterinarian = appointmentRepository.findByVeterinarianIsNull();

        return consultationsWithoutVeterinarian.stream().map(appointmentMapper::toResponseDto).collect(Collectors.toList());
    }

    public void setVeterinarianAvailable(UUID veterinarianId, UUID appointmentId){
        User userLogged = userSecurityHelper.getAuthenticatedUser();

        if(userLogged.getRole() != UserRole.ADMIN){
            throw new AccessDeniedException("Acesso Negado!, Você não é ADMIN");
        }

        User veterinarian = adminService.getById(veterinarianId);

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Consulta Não encontrada"));

        veterinarian.addAppointment(appointment);
        appointment.defineVeterinarian(veterinarian);

        appointmentRepository.save(appointment);
    }

}
