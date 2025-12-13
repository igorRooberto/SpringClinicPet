package com.example.SpringClinicPet.dto.PetDto;

import com.example.SpringClinicPet.model.enums.PetSpecies;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PetRequestDto(
        @NotBlank String name,
        @NotBlank String breed,
        @NotNull PetSpecies specie) {
}
