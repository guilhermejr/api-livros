package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa uma editora DTO")
public class EditoraDTO {

	@Schema(description = "ID da editora", example = "1")
	private Long id;
	
	@Schema(description = "Nome da editora", example = "Zahar")
	private String descricao;
	
	@Schema(description = "Data da criação")
	private LocalDateTime criado;

}
