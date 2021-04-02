package net.guilhermejr.apilivros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiLivrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiLivrosApplication.class, args);
	}

}
