package com.example.SpringClinicPet.controller;

import com.example.SpringClinicPet.dto.PetDto.PetRequestDto;
import com.example.SpringClinicPet.dto.PetDto.PetResponseDto;
import com.example.SpringClinicPet.services.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping("/create/pet")
    public ResponseEntity<PetResponseDto> createNewPet(@RequestBody @Valid PetRequestDto data){

        PetResponseDto pet = petService.createNewPet(data);

        return new ResponseEntity<>(pet,HttpStatus.CREATED);
    }

    @PatchMapping("/delete/pet/{petId}")
    public ResponseEntity<Void> deletePet(@PathVariable UUID petId){
        petService.deletePet(petId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/mypets")
    public ResponseEntity<List<PetResponseDto>> getPetByOwner(){
        List<PetResponseDto> pets = petService.getPetsByOwner();

        return new ResponseEntity<>(pets,HttpStatus.OK);
    }


}
