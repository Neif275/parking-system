package com.estacionamiento.tariff.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS-Tariff API")
                        .description("Microservicio de gestión de tarifas por tipo de vehículo")
                        .version("1.0.0"));
    }
}
