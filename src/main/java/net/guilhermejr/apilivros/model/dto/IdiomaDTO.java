package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa um idioma DTO")
public class IdiomaDTO {

	@Schema(description = "ID do idioma", example = "1")
	private Long id;
	
	@Schema(description = "Nome do idioma", example = "português")
	private String descricao;
	
	@Schema(description = "Data da criação")
	private LocalDateTime criado;

}
