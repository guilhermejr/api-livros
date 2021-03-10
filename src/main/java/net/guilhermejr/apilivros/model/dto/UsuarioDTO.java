package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioDTO {
	
	private Long id;
	private String nome;
	private String email;
	private Boolean ativo;
	private LocalDateTime criado;
	private LocalDateTime ultimoAcesso;

}
