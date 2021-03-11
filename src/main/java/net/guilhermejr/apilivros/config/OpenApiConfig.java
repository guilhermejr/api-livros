package net.guilhermejr.apilivros.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {
	
	@Bean
	public OpenAPI openApi() {
		return new OpenAPI()
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
