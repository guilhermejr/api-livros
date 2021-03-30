package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditoraDTO {
	
	private Long id;
	private String descricao;
	private LocalDateTime criado;

}
