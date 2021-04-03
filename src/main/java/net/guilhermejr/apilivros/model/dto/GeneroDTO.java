package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa um gênero DTO")
public class GeneroDTO {

	@Schema(description = "ID do gênero", example = "1")
	private Long id;
	
	@Schema(description = "Nome do gênero", example = "Fantasia")
	private String descricao;
	
	@Schema(description = "Data da criação")
	private LocalDateTime criado;

}
