package com.example.SpringClinicPet.mapper;

import com.example.SpringClinicPet.dto.PetDto.PetResponseDto;
import com.example.SpringClinicPet.model.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetResponseDto toResponseDto(Pet pet){
        return new PetResponseDto(
                pet.getId(),
                pet.getBreed(),
                pet.getName(),
                pet.getSpecies()
        );
    }

}
