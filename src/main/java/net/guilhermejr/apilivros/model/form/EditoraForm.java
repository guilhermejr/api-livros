package net.guilhermejr.apilivros.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import net.guilhermejr.apilivros.model.annotation.EditoraUnica;

@Getter
@Setter
@Schema(description = "Representa uma editora Form")
public class EditoraForm {
	
	@Schema(description = "Nome da editora", example = "Zahar")
    @NotBlank()
    @Size(max = 255)
    @EditoraUnica()
	private String descricao;

}
