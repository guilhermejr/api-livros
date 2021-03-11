package net.guilhermejr.apilivros.model.form;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroForm {
	
	private String capa;
	private String extensao;
	private String titulo;
	private String subTitulo;
	private String isbn13;
	private String descricao;
	private List<String> generos;
	private List<String> autores;
	private String editora;
	private String idioma;
	private String anoPublicacao;
	private String paginas;
	private String tipo = "1";
	private String estante;

}
