package net.guilhermejr.apilivros.validacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Representa um retorno de erro de preenchimento de formulário")
public class ErroDeFormularioDTO {
	
	@Schema(description = "Campo com erro", example = "descricao")
	private String campo;
	
	@Schema(description = "Mensagem de erro", example = "Descrição não pode ser em branco.")
	private String mensagem;

}
