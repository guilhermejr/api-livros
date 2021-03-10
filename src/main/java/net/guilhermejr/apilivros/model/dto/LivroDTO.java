package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LivroDTO {
	
	private Long id;
	private String capa;
	private String titulo;
	private String subTitulo;
	private String isbn;
	private String descricao;
	private EditoraDTO editora;
	private IdiomaDTO idioma;
	private int ano;
	private int paginas;
	private LocalDateTime criado;
	private List<AutorDTO> autores;
	private List<GeneroDTO> generos;
	private TipoDTO tipo;
	private Boolean tenho;
	private UsuarioDTO usuario;

}
