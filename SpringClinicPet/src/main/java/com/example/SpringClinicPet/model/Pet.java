package com.example.SpringClinicPet.model;

import com.example.SpringClinicPet.model.enums.PetSpecies;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_pet")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,unique = true)
    private UUID id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "breed",nullable = false)
    private String breed;

    @Enumerated(EnumType.STRING)
    @Column(name = "species",nullable = false)
    private PetSpecies species;

    @Column(name = "enabled",nullable = false)
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "owner_id",nullable = false)
    private User owner;

    @OneToMany(mappedBy = "pet",fetch = FetchType.LAZY)
    private Set<Appointment> appointments =new HashSet<>();

    public Pet(String name, User owner, PetSpecies species, String breed, Boolean enabled) {
        this.name = name;
        this.owner = owner;
        this.species = species;
        this.breed = breed;
        this.enabled = enabled;
    }

    public void addAppointments(Appointment appointment){
        appointments.add(appointment);
    }

    public void deactivate(){
        this.enabled = false;
    }

}
