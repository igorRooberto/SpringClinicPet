package com.example.SpringClinicPet.services;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentRequestDto;
import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.dto.AppointmentDto.DiagnosisNotesRequestDto;
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
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserSecurityHelper userSecurityHelper;
    private final PetService petService;
    private final AdminService adminService;
    private final AppointmentMapper appointmentMapper;

    public AppointmentService(AppointmentMapper appointmentMapper,
                              AppointmentRepository appointmentRepository,
                              UserSecurityHelper userSecurityHelper,
                              PetService petService,
                              AdminService adminService) {

        this.appointmentMapper = appointmentMapper;
        this.appointmentRepository = appointmentRepository;
        this.userSecurityHelper = userSecurityHelper;
        this.petService = petService;
        this.adminService = adminService;
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

    public List<AppointmentResponseDto> getConsultationsWithoutVeterinarian(){
        List<Appointment> consultationsWithoutVeterinarian = appointmentRepository.findByVeterinarianIsNull();

        return consultationsWithoutVeterinarian.stream().map(appointmentMapper::toResponseDto).collect(Collectors.toList());
    }

    public List<AppointmentResponseDto> getConsultationsByVeterinarian(){
        User veterinarian = userSecurityHelper.getAuthenticatedUser();

        List<Appointment> appointmentList = appointmentRepository.findByVeterinarian(veterinarian);

        return appointmentList.stream().map(appointmentMapper::toResponseDto).collect(Collectors.toList());
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

    public Appointment getById(UUID id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id));
    }



}
