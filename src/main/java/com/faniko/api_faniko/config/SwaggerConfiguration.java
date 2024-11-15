package com.faniko.api_faniko.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Faniko API",
                version = "v1",
                description = "Faniko API Documentation. Faniko is an app for managing laundry services."
        ),
        security = @SecurityRequirement(name = "bearerAuth"),
        servers = {
                @Server(url = "/", description = "Default server URL")
        }
)
@SecuritySchemes(
        @SecurityScheme(
                name = "bearerAuth",
                type = SecuritySchemeType.HTTP,
                scheme = "bearer",
                bearerFormat = "JWT"
        )
)
public class SwaggerConfiguration {
}
