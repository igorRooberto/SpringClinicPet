package com.example.SpringClinicPet.services;

import com.example.SpringClinicPet.dto.registerVeterinarianDto.VeterinarianResponseDto;
import com.example.SpringClinicPet.infra.security.UserSecurityHelper;
import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.UserRole;
import com.example.SpringClinicPet.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final UserSecurityHelper userSecurityHelper;

    public AdminService(UserRepository userRepository, UserSecurityHelper userSecurityHelper) {
        this.userRepository = userRepository;
        this.userSecurityHelper = userSecurityHelper;
    }

    public User getById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User não encontrado com ID: " + id));
    }

    public void changeUserStatusActivate(UUID userId){
        User userLogged = userSecurityHelper.getAuthenticatedUser();

        if(!(userLogged.getRole() == UserRole.ADMIN)){
            throw new AccessDeniedException("Acesso Negado!");
        }

        User userActivate = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        userActivate.activate();

        userRepository.save(userActivate);
    }

    public void changeUserStatusDeactivate(UUID userId){
        User userLogged = userSecurityHelper.getAuthenticatedUser();

        if(!(userLogged.getRole() == UserRole.ADMIN)){
            throw new AccessDeniedException("Acesso Negado!");
        }

        User userDeactivate = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        userDeactivate.deactivate();

        userRepository.save(userDeactivate);
    }

    public List<VeterinarianResponseDto> findAllPendingVeterinarians(){
        User userLogged = userSecurityHelper.getAuthenticatedUser();

        if(!(userLogged.getRole() == UserRole.ADMIN)){
            throw new AccessDeniedException("Acesso Negado!");
        }

        List<User> veterinarians = userRepository.findUsersByRoleAndEnabledFalse(UserRole.VETERINARIAN);

        return veterinarians.stream().map(user -> new VeterinarianResponseDto(
                user.getId(),
                user.getLogin(),
                user.getCrvm(),
                user.isEnabled())
        ).toList();
    }

    public List<VeterinarianResponseDto> findAllVeterinariansActivate(){

        User userLogged = userSecurityHelper.getAuthenticatedUser();

        if(!(userLogged.getRole() == UserRole.ADMIN)){
            throw new AccessDeniedException("Acesso Negado!");
        }

        List<User> veterinarians = userRepository.findUsersByRoleAndEnabledTrue(UserRole.VETERINARIAN);

        return veterinarians.stream().map(user -> new VeterinarianResponseDto(
                user.getId(),
                user.getLogin(),
                user.getCrvm(),
                user.isEnabled())
        ).toList();
    }

}
