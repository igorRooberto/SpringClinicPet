package com.example.SpringClinicPet.services;

import com.example.SpringClinicPet.dto.AppointmentDto.AppointmentResponseDto;
import com.example.SpringClinicPet.dto.PetDto.PetRequestDto;
import com.example.SpringClinicPet.dto.PetDto.PetResponseDto;
import com.example.SpringClinicPet.infra.security.UserSecurityHelper;
import com.example.SpringClinicPet.mapper.PetMapper;
import com.example.SpringClinicPet.model.Pet;
import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.UserRole;
import com.example.SpringClinicPet.repository.PetRepository;
import com.example.SpringClinicPet.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final UserSecurityHelper userSecurityHelper;
    private final PetMapper petMapper;

    public PetService(PetMapper petMapper, PetRepository petRepository, UserSecurityHelper userSecurityHelper) {
        this.petMapper = petMapper;
        this.petRepository = petRepository;
        this.userSecurityHelper = userSecurityHelper;
    }

    public Pet getById(UUID id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet não encontrado com ID: " + id));
    }

    public PetResponseDto createNewPet(PetRequestDto data){

        User owner = userSecurityHelper.getAuthenticatedUser();

        Pet pet = new Pet(data.name(), owner, data.specie(), data.breed(), true);

        owner.addPet(pet);
        petRepository.save(pet);

        return petMapper.toResponseDto(pet);
    }

    public void deletePet(UUID petId){
        User userLogged = userSecurityHelper.getAuthenticatedUser();

        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet Não Encontrado Erro"));

        if(!(userLogged.getId().equals(pet.getOwner().getId()))){
            throw new AccessDeniedException("Acesso Negado");
        }

        pet.deactivate();

        petRepository.save(pet);
    }

    public List<PetResponseDto> getPetsByOwner(){
        User owner = userSecurityHelper.getAuthenticatedUser();

        if(owner.getRole() == UserRole.VETERINARIAN || owner.getRole() == UserRole.ADMIN){
            return List.of();
        }

        List<Pet> pets = petRepository.findByOwnerAndEnabledTrue(owner);

        return pets.stream().map(petMapper::toResponseDto).collect(Collectors.toList());
    }

}
