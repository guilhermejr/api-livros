package net.guilhermejr.apilivros.model.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "Representa uma troca se senha Form")
public class TrocaSenhaForm {
	
	@Schema(description = "Senha atual", example = "C4m1l4@@")
	@NotBlank()
	private String senhaAtual;
	
	@Schema(description = "Nova senha", example = "!C4m1l4@@!")
	@NotBlank()
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	private String senhaNova;
	
	@Schema(description = "Confirmar nova senha", example = "!C4m1l4@@!")
	@NotBlank()
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	private String senhaNovaConfirmar;

}
