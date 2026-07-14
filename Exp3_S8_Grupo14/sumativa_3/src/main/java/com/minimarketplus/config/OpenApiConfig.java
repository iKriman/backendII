package com.minimarketplus.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    OpenAPI minimarketPlusOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Minimarket Plus API")
                        .description("Documentacion avanzada de endpoints REST con OpenAPI y HATEOAS.")
                        .version("1.0.0")
                        .contact(new Contact().name("Equipo Backend II").email("backend@minimarketplus.cl"))
                        .license(new License().name("Uso academico")));
    }
}
