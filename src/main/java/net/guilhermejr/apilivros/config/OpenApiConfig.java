package net.guilhermejr.apilivros.config;

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
	
	@Bean
	public OpenAPI openApi() {
		final String securitySchemeName = "Bearer Token";
		return new OpenAPI()
				.addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
		        .components(new Components().addSecuritySchemes(securitySchemeName,
		            new SecurityScheme().name(securitySchemeName)
		                .type(SecurityScheme.Type.HTTP)
		                .scheme("bearer")
		                .bearerFormat("JWT")))
				.info(new Info()
						.title("Api de livros")
						.description("Descrição api de livros")
						.version("v1")
						.contact(new Contact()
								.name("Guilherme Jr.")
								.email("falecom@guilhermejr.net")
								.url("https://www.guilhermejr.net")));
	}

}
