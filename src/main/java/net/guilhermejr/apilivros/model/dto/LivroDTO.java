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
	private String extensao;
	private String titulo;
	private String subtitulo;
	private String isbn;
	private String descricao;
	private EditoraDTO editora;
	private IdiomaDTO idioma;
	private int anoPublicacao;
	private int paginas;
	private LocalDateTime criado;
	private LocalDateTime atualizado;
	private List<AutorDTO> autores;
	private List<GeneroDTO> generos;
	private TipoDTO tipo;
	private EstanteDTO estante;
	private UsuarioDTO usuario;

}
