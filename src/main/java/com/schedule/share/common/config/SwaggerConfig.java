package com.schedule.share.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    SecurityScheme apiAuth = new SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .in(SecurityScheme.In.HEADER)
            .scheme("Bearer")
            .bearerFormat("JWT")
            .name("Authorization");

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo())
                .components(new Components()
                        .addSecuritySchemes("Authorization", apiAuth)
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("Authorization"))
                .addSecurityItem(new SecurityRequirement()
                        .addList("Authorization"));
    }

    private Info apiInfo() {
        return new Info()
                .title("Share Schedule - user API")
                .version("1.0.0");
    }

}
