package net.penguincoders.budgetplanner.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(servers())
                .security(security())
                .components(components());
    }

    private Info apiInfo() {
        return new Info()
                .title("Budget Planner API")
                .description("Application to track expenses and finance")
                .version("1.0.0")
                .contact(apiContact())
                .license(apiLicence());
    }

    private License apiLicence() {
        return new License()
                .name("Apache 2.0")
                .url("http://www.apache.org/licenses/LICENSE-2.0.html");
    }

    private Contact apiContact() {
        return new Contact()
                .name("Penguin Coders")
                .email("mohit@penguincoders.net")
                .url("https://youtube.com/PenguinCodersNetOfficial");
    }

    private List<Server> servers() {
        return List.of(
                new Server().description("Local").url("http://localhost:8080")
        );
    }

    private List<SecurityRequirement> security() {
        return List.of(new SecurityRequirement().addList("bearerAuth"));
    }

    private Components components() {
        return new Components().addSecuritySchemes("bearerAuth", new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT"));
    }
}

