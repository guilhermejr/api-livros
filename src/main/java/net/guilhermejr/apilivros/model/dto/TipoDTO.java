package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa um tipo DTO")
public class TipoDTO {

	@Schema(description = "ID do tipo", example = "1")
	private Long id;
	
	@Schema(description = "Nome do tipo", example = "Físico")
	private String descricao;
	
	@Schema(description = "Data da criação")
	private LocalDateTime criado;

}
