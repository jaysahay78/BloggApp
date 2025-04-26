package com.spring.blogApp_api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI BloggingAPI() {

        String schemeName = "bearerScheme";
        return new OpenAPI()

                .addSecurityItem(new SecurityRequirement()
                        .addList(schemeName)

                )
                .components(new Components()
                        .addSecuritySchemes(schemeName, new SecurityScheme()
                                .name(schemeName)
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer")
                        )
                )
                .info(new Info()
                        .title("Blogging application Api")
                        .description("Blogging application Api developed by jaysahay78")
                        .version("1.0")
                        .license(new License().name("Apache 2.0"))
                ).externalDocs(new ExternalDocumentation()
                        .url("http://localhost:8080/api/")
                        .description("this is main url as no external url present"));
    }
}
