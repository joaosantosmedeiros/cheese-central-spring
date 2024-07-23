package joao.pedro.productsapi.infrastructure.config.springdoc;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SpringDocConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT"))
                ).info(new Info()
                        .title("Cheese Central API")
                        .description("API simulando um E-commerce completo.")
                        .contact(new Contact()
                                .name("João Pedro dos Santos Medeiros")
                                .email("jopesame@gmail.com"))
                ).servers(List.of(
                        new Server().url("http://localhost:8080").description("API url")
                ));
    }
}