package net.guilhermejr.apilivros.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import net.guilhermejr.apilivros.model.annotation.TipoUnico;

@Getter
@Setter
public class TipoForm {
	
	private Long id;
	
    @NotBlank()
    @Size(max = 255)
    @TipoUnico()
	private String descricao;

}
