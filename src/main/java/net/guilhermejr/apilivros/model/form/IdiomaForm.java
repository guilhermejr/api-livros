package net.guilhermejr.apilivros.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import net.guilhermejr.apilivros.model.annotation.IdiomaUnico;

@Getter
@Setter
@Schema(description = "Representa um idioma Form")
public class IdiomaForm {

	@Schema(description = "Nome do idioma", example = "português")
    @NotBlank()
    @Size(max = 255)
    @IdiomaUnico()
	private String descricao;

}
