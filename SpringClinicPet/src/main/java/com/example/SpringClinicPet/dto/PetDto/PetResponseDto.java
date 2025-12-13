package com.example.SpringClinicPet.dto.PetDto;

import com.example.SpringClinicPet.model.enums.PetSpecies;

import java.util.UUID;

public record PetResponseDto(UUID id, String name, String breed, PetSpecies specie) {
}
