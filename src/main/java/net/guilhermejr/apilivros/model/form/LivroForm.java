package net.guilhermejr.apilivros.model.form;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.guilhermejr.apilivros.model.annotation.ISBNUnico;

@Getter
@Setter
@ToString
public class LivroForm {
	
	@NotBlank()
    @Size(max = 255)
	private String capa;
	
	@NotBlank()
    @Size(max = 255)
	private String extensao;
	
	@NotBlank()
    @Size(max = 255)
	private String titulo;
	
    @Size(max = 255)
	private String subTitulo;
	
	@NotBlank()
	@ISBNUnico
	@Size(min = 13, max = 13)
	private String isbn13;
	
	@NotBlank()
	private String descricao;
	
	@NotEmpty()
	private List<String> generos;
	
	@NotEmpty()
	private List<String> autores;
	
	@NotBlank()
	private String editora;
	
	@NotBlank()
	private String idioma;
	
	@Min(1900)
	@Max(2100)
	private String anoPublicacao;
	
	@NotNull()
	@Digits(integer = 10, fraction = 0)
	private String paginas = "0";
	
	@NotNull()
	@Min(1)
	@Max(3)
	private String tipo = "1";
	
	@NotNull()
	@Min(1)
	@Max(4)
	private String estante = "1";

}
