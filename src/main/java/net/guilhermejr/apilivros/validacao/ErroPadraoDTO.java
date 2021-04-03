package net.guilhermejr.apilivros.validacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Representa um retorno de erro padrão")
public class ErroPadraoDTO {

	@Schema(description = "Status code do retorno", example = "404")
	int status;
	
	@Schema(description = "Detalhe do erro", example = "Recurso não encontrado.")
	String detalhe;
    
}
