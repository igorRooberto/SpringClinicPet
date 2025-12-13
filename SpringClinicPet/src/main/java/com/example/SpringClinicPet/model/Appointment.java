package com.example.SpringClinicPet.model;

import com.example.SpringClinicPet.model.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_appointment")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",nullable = false,unique = true)
    private UUID id;

    @Column(name = "date_time",nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "reason",nullable = false,columnDefinition = "TEXT")
    private String reason;

    @Column(name = "diagnosis_notes",columnDefinition = "TEXT")
    private String diagnosisNotes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private AppointmentStatus status;

    @ManyToOne
    @JoinColumn(name = "pet_id",nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "veterinarian_id",nullable = true)
    private User veterinarian;

    public Appointment(LocalDateTime dateTime, String reason, AppointmentStatus status, String diagnosisNotes,User veterinarian ,Pet pet) {
        this.dateTime = dateTime;
        this.reason = reason;
        this.status = status;
        this.diagnosisNotes = diagnosisNotes;
        this.pet = pet;
        this.veterinarian = veterinarian;
    }

    public void defineVeterinarian(User veterinarian){
        this.veterinarian = veterinarian;
    }

    public void cancelConsultation(){
        if(this.status == AppointmentStatus.COMPLETED){
            throw new IllegalStateException("Não é possível cancelar uma consulta já realizada.");
        }
        if (this.status == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Esta consulta já está cancelada.");
        }

        this.status = AppointmentStatus.CANCELLED;
    }

    public boolean belongsTo(User user) {
        boolean isPetOwner = this.pet.getOwner().getId().equals(user.getId());

        boolean isResponsibleVeterinarian = this.veterinarian != null
                && this.veterinarian.getId().equals(user.getId());

        return isPetOwner || isResponsibleVeterinarian;
    }

    public boolean belongsVeterinarian(User user){
        boolean isResponsibleVeterinarian =  this.veterinarian != null
                && this.veterinarian.getId().equals(user.getId());

        return isResponsibleVeterinarian;
    }

    public void finalizedConsultation(String diagnosisNotes){
        this.diagnosisNotes = diagnosisNotes;

        this.status = AppointmentStatus.COMPLETED;
    }
}
