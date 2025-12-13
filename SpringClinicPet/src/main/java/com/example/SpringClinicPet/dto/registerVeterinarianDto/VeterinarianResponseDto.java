package com.example.SpringClinicPet.dto.registerVeterinarianDto;

import java.util.UUID;

public record VeterinarianResponseDto(UUID id
                                             , String login
                                             , String crvm
                                             , Boolean enabled  ) {
}
