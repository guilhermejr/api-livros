package net.guilhermejr.apilivros.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import net.guilhermejr.apilivros.model.annotation.AutorUnico;

@Getter
@Setter
@Schema(description = "Representa um autor Form")
public class AutorForm {

	@Schema(description = "Nome do autor", example = "Sófocles")
    @NotBlank()
    @Size(max = 255)
    @AutorUnico
	private String descricao;
	
}
