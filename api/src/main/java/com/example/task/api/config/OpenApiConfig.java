package com.example.task.api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI taskManagementOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Task Management Service API")
                        .description("REST API for managing tasks using clean architecture")
                        .version("1.0.0"));
    }
}
