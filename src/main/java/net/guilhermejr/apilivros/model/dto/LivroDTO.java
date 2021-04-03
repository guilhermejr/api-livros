package net.guilhermejr.apilivros.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Representa um livro DTO")
public class LivroDTO {

	@Schema(description = "ID do livro", example = "1")
	private Long id;
	
	@Schema(description = "Url da capa do livro no Skoob", example = "https://cache.skoob.com.br/local/images//j1vthArCB8H7Pf4RWT09htUMewQ=/200x/center/top/smart/filters:format(jpeg)/https://skoob.s3.amazonaws.com/livros/327873/A_CIVILIZACAO_DO_ESPETACULO__1370882383B.jpg")
	private String capa;
	
	@Schema(description = "Extensão da capa do livro", example = "jpg")
	private String extensao;
	
	@Schema(description = "Título do livro", example = "O Melhor do Teatro Grego")
	private String titulo;
	
	@Schema(description = "Subtítulo do livro", example = "Prometeu Acorrentado - Édipo Rei - Medeia - As Nuvens")
	private String subtitulo;
	
	@Schema(description = "ISBN do livro", example = "9788537810743")
	private String isbn;
	
	@Schema(description = "Descrição do livro", example = "Ideal para quem deseja se familiarizar com o teatro clássico, esse livro reúne os seus autores mais importantes, Aristófanes, Ésquilo, Eurípides e Sófocles, representados por quatro peças que estão na base da cultura ocidental, as tragédias: Prometeu Acorrentado, Medéia , Édipo Rei e a comédia As Nuvens. A tradução, diretamente do grego, foi feita pelo renomado Mário da Gama Kury. Essa edição oferece um vasto material de apoio, que facilitará a leitura mesmo daqueles que nunca tiveram contato com as grandes peças teatrais, como textos introdutórios, resumo da ação em cada peça, perfis dos personagens, glossário e mais de 190 notas.<br><br>")
	private String descricao;
	
	private EditoraDTO editora;
	private IdiomaDTO idioma;
	
	@Schema(description = "Ano lançamento do livro", example = "2013")
	private int anoPublicacao;
	
	@Schema(description = "Quantidade de páginas do livro", example = "392")
	private int paginas;
	
	@Schema(description = "Data da criação")
	private LocalDateTime criado;
	
	@Schema(description = "Data da atualização")
	private LocalDateTime atualizado;
	
	@Schema(description = "Define se o livro está ativo", example = "true")
	private Boolean ativo;
	
	private List<AutorDTO> autores;
	private List<GeneroDTO> generos;
	private TipoDTO tipo;
	private EstanteDTO estante;
	private UsuarioDTO usuario;

}
