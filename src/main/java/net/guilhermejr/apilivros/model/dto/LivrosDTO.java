package net.guilhermejr.apilivros.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa livros DTO")
public class LivrosDTO {

	@Schema(description = "ID do livro", example = "1")
	private Long id;
	
	@Schema(description = "Título do livro", example = "O Melhor do Teatro Grego")
	private String titulo;

}
