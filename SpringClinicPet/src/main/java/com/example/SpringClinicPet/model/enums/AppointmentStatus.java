package com.example.SpringClinicPet.model.enums;

public enum AppointmentStatus {

    SCHEDULED("SCHEDULED"),
    COMPLETED("COMPLETED"),
    CANCELLED("CANCELLED"),
    NO_SHOW("NO_SHOW");

    private String appointmentStatus;

    AppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }
}
