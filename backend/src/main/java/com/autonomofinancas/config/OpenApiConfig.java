package com.autonomofinancas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class OpenApiConfig {

    private static final String ESQUEMA_SEGURANCA = "bearerAuth";

    @Bean
    public OpenAPI configurarOpenApi() {

        return new OpenAPI()
                .info(new Info()
                        .title("Autônomo Finanças API")
                        .description(
                                "API para controle financeiro de trabalhadores autônomos, "
                                        + "com gestão de receitas, despesas, categorias, plataformas "
                                        + "e indicadores financeiros.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Guilherme Henrique Froes")))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(ESQUEMA_SEGURANCA))
                .components(new Components()
                        .addSecuritySchemes(
                                ESQUEMA_SEGURANCA,
                                new SecurityScheme()
                                        .name(ESQUEMA_SEGURANCA)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}