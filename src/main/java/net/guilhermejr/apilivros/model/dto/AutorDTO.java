package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa um autor DTO")
public class AutorDTO {
	
	@Schema(description = "ID do autor", example = "1")
	private Long id;
	
	@Schema(description = "Nome do autor", example = "Sófocles")
	private String descricao;
	
	@Schema(description = "Data da criação")
	private LocalDateTime criado;

}
