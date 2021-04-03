package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa um usuário DTO")
public class UsuarioDTO {

	@Schema(description = "ID do usuário", example = "1")
	private Long id;
	
	@Schema(description = "Nome do usuário", example = "Guilherme Jr.")
	private String nome;
	
	@Schema(description = "E-mail do usuário", example = "falecom@guilhermejr.net")
	private String email;
	
	@Schema(description = "Define se o usuário está ativo", example = "true")
	private Boolean ativo;
	
	@Schema(description = "Data da criação")
	private LocalDateTime criado;
	
	@Schema(description = "Data do último acesso ao sistema")
	private LocalDateTime ultimoAcesso;

}
