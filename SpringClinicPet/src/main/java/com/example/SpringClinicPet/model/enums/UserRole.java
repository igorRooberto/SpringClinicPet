package com.example.SpringClinicPet.model.enums;

public enum UserRole{
    ADMIN("ADMIN"),
    VETERINARIAN("VETERINARIAN"),
    USER("USER");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
