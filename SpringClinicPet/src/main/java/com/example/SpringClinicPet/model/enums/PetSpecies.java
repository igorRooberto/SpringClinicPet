package com.example.SpringClinicPet.model.enums;

public enum PetSpecies {

    CANINE("CANINE"),
    FELINE("FELINE"),
    AVIAN("AVIAN"),
    REPTILE("REPTILE"),
    RODENT("RODENT"),
    OTHER("OTHER");

    private String Specie;

    PetSpecies(String specie) {
        Specie = specie;
    }

    public String getSpecie() {
        return Specie;
    }
}
