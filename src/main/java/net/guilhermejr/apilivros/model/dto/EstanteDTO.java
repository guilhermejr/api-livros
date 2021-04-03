package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa um estante DTO")
public class EstanteDTO {
	
	@Schema(description = "ID da estante", example = "1")
	private Long id;
	
	@Schema(description = "ID da estante", example = "Biblioteca")
	private String descricao;
	
	@Schema(description = "Data da criação")
	private LocalDateTime criado;

}
