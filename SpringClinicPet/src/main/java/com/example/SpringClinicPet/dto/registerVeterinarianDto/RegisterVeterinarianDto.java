package com.example.SpringClinicPet.dto.registerVeterinarianDto;

import jakarta.validation.constraints.NotBlank;

public record RegisterVeterinarianDto(@NotBlank String login
                                    , @NotBlank String password
                                    , @NotBlank String crvm) {
}
