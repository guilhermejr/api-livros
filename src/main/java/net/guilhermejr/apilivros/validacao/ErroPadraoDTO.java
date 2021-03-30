package net.guilhermejr.apilivros.validacao;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErroPadraoDTO {

	int status;
	String detalhe;
    
}
