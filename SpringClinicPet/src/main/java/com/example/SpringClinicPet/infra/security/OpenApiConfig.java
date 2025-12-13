package com.example.SpringClinicPet.infra.security;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")

                        )
                )

                .addSecurityItem(new SecurityRequirement().addList("bearer-key"))

                .info(new Info()
                        .title("Spring_Clinic_Pet")
                        .description("API Rest da clínica veterinária, contendo as funcionalidades de CRUD " +
                                "de médicos e pacientes, além de agendamento de consultas")

                        .contact(new Contact()
                                .name("Igor Roberto")
                                .email("isilvalopp@gmail.com")
                        )

                        .license(new License()
                                .name("Apache 2.0")
                        )


                );
    }

}
