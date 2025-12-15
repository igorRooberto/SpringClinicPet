package com.example.SpringClinicPet.dto.registerVeterinarianDto;

import jakarta.validation.constraints.NotBlank;

public record RegisterVeterinarianDto(@NotBlank(message = "o Login de Usuário é obrigatório")
                                      String login,

                                     @NotBlank(message = "A senha é obrigatória")
                                     String password,

                                      @NotBlank(message = "O CÓDIGO DE VETERINÁRIO É OBRIGATÓRIO")
                                      String crvm,

                                      @NotBlank(message = "O email é obrigatório")
                                      String email

) {
}
