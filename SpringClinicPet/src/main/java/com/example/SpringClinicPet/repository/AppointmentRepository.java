package com.example.SpringClinicPet.repository;

import com.example.SpringClinicPet.model.Appointment;
import com.example.SpringClinicPet.model.Pet;
import com.example.SpringClinicPet.model.User;
import com.example.SpringClinicPet.model.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, UUID> {

    List<Appointment> findByVeterinarianIsNull();

    List<Appointment> findByVeterinarian(User veterinarian);

    List<Appointment> findByPet(Pet pet);


}
