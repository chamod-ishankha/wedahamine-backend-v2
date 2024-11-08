package org.bytecub.WedahamineBackend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Wedahamine Backend API Documentation",
                version = "v1.0",
                description = "API for managing Wedahamine backend services",
                termsOfService = "Terms of service",
                contact = @Contact(
                        name = "ByteCub Organization",
                        email = "bytecub@admin.org",
                        url = "https://bytecub.org"
                ),
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8080/api/v1/baby-care"
                )
        }
)
@SecurityScheme(
        name = "BearerAuth",
        description = "Bearer Token Authentication",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class SwaggerConfig {

}