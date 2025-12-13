package com.example.SpringClinicPet.dto.AuthenticateDto;

import jakarta.validation.constraints.NotBlank;

public record RegisterDto(@NotBlank(message = "O login é obrigatório")
                          String login,

                          @NotBlank(message = "A senha é obrigatória")
                          String password) {
}
