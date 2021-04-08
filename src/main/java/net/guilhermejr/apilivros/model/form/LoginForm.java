package net.guilhermejr.apilivros.model.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "Representa um livro Form")
public class LoginForm {
	
	@Schema(description = "E-mail do usuário", example = "nome@dominio.com")
	@NotBlank()
	@Email()
	private String email;
	
	@Schema(description = "Senha do usuário", example = "1234567890")
	@NotBlank()
	private String senha;

}
