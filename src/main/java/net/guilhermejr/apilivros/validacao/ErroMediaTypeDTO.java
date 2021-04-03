package net.guilhermejr.apilivros.validacao;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Representa um retorno de erro de MediaType")
public class ErroMediaTypeDTO {

	@Schema(description = "Status code do retorno", example = "415")
	int status;
	
	@Schema(description = "Detalhe do erro", example = "Content-Type não suportado.")
	String detalhe;
    
}
