package net.guilhermejr.apilivros.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import net.guilhermejr.apilivros.model.annotation.IdiomaUnico;

@Getter
@Setter
public class IdiomaForm {
	
	private Long id;
	
    @NotBlank()
    @Size(max = 255)
    @IdiomaUnico()
	private String descricao;

}
