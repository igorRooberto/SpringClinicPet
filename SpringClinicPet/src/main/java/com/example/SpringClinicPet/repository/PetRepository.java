package com.example.SpringClinicPet.repository;

import com.example.SpringClinicPet.model.Pet;
import com.example.SpringClinicPet.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface PetRepository extends JpaRepository<Pet, UUID> {

    List<Pet> findByOwner(User owner);

    List<Pet> findByOwnerAndEnabledTrue(User owner);



}
