package net.guilhermejr.apilivros.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import net.guilhermejr.apilivros.model.annotation.GeneroUnico;

@Getter
@Setter
@Schema(description = "Representa um gênero Form")
public class GeneroForm {

	@Schema(description = "Nome do gênero", example = "Fantasia")
    @NotBlank()
    @Size(max = 255)
    @GeneroUnico()
	private String descricao;
}
