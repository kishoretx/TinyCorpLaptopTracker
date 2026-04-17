package com.tinycorp.laptoptracker.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Tiny Corp Laptop Tracker API")
                        .version("v1")
                        .description("Asset tracking APIs for users, devices, assignments, authentication, and reports"))
                .addServersItem(new Server().url("http://localhost:8080").description("Local Development"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .schemaRequirement("bearerAuth",
                        new io.swagger.v3.oas.models.security.SecurityScheme().type(Type.HTTP).scheme("bearer")
                                .bearerFormat("JWT"));
    }
}
