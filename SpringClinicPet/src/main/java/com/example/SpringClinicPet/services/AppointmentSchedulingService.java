package com.example.SpringClinicPet.services;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentRequestDto;
import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.infra.security.UserSecurityHelper;
import com.example.SpringClinicPet.mapper.AppointmentMapper;
import com.example.SpringClinicPet.model.Appointment;
import com.example.SpringClinicPet.model.Pet;
import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.AppointmentStatus;
import com.example.SpringClinicPet.model.enums.UserRole;
import com.example.SpringClinicPet.repository.AppointmentRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AppointmentSchedulingService {

    private final AppointmentRepository appointmentRepository;
    private final PetService petService;
    private final UserSecurityHelper userSecurityHelper;
    private final AppointmentMapper appointmentMapper;

    public AppointmentSchedulingService(AppointmentMapper appointmentMapper,
                                        AppointmentRepository appointmentRepository,
                                        PetService petService,
                                        UserSecurityHelper userSecurityHelper) {

        this.appointmentMapper = appointmentMapper;
        this.appointmentRepository = appointmentRepository;
        this.petService = petService;
        this.userSecurityHelper = userSecurityHelper;
    }

    public AppointmentResponseDto scheduleConsultation(AppointmentRequestDto data){
        User owner = userSecurityHelper.getAuthenticatedUser();

        Pet pet = petService.getById(data.petId());

        if(!(pet.getOwner().getId().equals(owner.getId()))){
            throw new RuntimeException("Acesso negado: Você não é dono deste animal");
        }

        Appointment newAppointment = new Appointment(
                data.dateTime(),
                data.reason(),
                AppointmentStatus.SCHEDULED,
                null,
                null,
                pet);

        pet.addAppointments(newAppointment);
        appointmentRepository.save(newAppointment);

        return appointmentMapper.toResponseDto(newAppointment);
    }

    public void cancelConsultation(UUID appointmentId){
        User owner = userSecurityHelper.getAuthenticatedUser();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Erro Consulta Não encontrado"));

        if(!(appointment.belongsTo(owner))){
            throw new AccessDeniedException("Acesso Negado : Essa Consulta Não é Sua");
        }

        appointment.cancelConsultation();
    }

    public List<AppointmentResponseDto> getConsultationsByPet(UUID petId){
        User userLogged = userSecurityHelper.getAuthenticatedUser();

        if(userLogged.getRole() == UserRole.ADMIN || userLogged.getRole() == UserRole.VETERINARIAN){
            return List.of();
        }

        Pet pet = petService.getById(petId);

        List<Appointment> appointmentList = appointmentRepository.findByPet(pet);

        return appointmentList.stream()
                .filter(appointment -> appointment.belongsTo(userLogged))
                .map(appointmentMapper::toResponseDto).collect(Collectors.toList());
    }

    public AppointmentResponseDto verifyConsultation(UUID appointmentId){
        User userLogged = userSecurityHelper.getAuthenticatedUser();

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Erro Não Consulta Encontrada"));

        if(!(appointment.belongsTo(userLogged))){
            throw new RuntimeException("Acesso Negado : Você não tem acesso a essa consulta");
        }

        return appointmentMapper.toResponseDto(appointment);
    }


}
