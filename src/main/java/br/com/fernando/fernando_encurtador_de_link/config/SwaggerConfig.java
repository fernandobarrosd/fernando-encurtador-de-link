package br.com.fernando.fernando_encurtador_de_link.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
    @Bean
    OpenAPI openAPI() {
        return new OpenAPI()
        .info(
            new Info()
            .title("API de Encurtamento de link")
            .description("Uma API para gerar link encurtados")
            .contact(
                new Contact()
                .name("Fernando de Barros")
                .url("https://www.linkedin.com/in/fernando-de-barros-204864241/")
            )
        );
    }
}