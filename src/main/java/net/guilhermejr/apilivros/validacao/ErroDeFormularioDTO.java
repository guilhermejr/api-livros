package net.guilhermejr.apilivros.validacao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ErroDeFormularioDTO {
	
	private String campo;
	private String mensagem;

}
