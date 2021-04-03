package net.guilhermejr.apilivros.model.form;

import java.util.List;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import net.guilhermejr.apilivros.model.annotation.ISBNUnico;

@Getter
@Setter
@ToString
@Schema(description = "Representa um livro Form")
public class LivroForm {
	
	@Schema(description = "Url da capa do livro no Skoob", example = "https://cache.skoob.com.br/local/images//j1vthArCB8H7Pf4RWT09htUMewQ=/200x/center/top/smart/filters:format(jpeg)/https://skoob.s3.amazonaws.com/livros/327873/A_CIVILIZACAO_DO_ESPETACULO__1370882383B.jpg")
	@NotBlank()
    @Size(max = 255)
	private String capa;
	
	@Schema(description = "Extensão da capa do livro", example = "jpg")
	@NotBlank()
    @Size(max = 255)
	private String extensao;

	@Schema(description = "Título do livro", example = "O Melhor do Teatro Grego")
	@NotBlank()
    @Size(max = 255)
	private String titulo;

	@Schema(description = "Subtítulo do livro", example = "Prometeu Acorrentado - Édipo Rei - Medeia - As Nuvens")
    @Size(max = 255)
	private String subtitulo;

	@Schema(description = "ISBN do livro", example = "9788537810743")
	@NotBlank()
	@ISBNUnico
	@Size(min = 13, max = 13)
	private String isbn13;

	@Schema(description = "Descrição do livro", example = "Ideal para quem deseja se familiarizar com o teatro clássico, esse livro reúne os seus autores mais importantes, Aristófanes, Ésquilo, Eurípides e Sófocles, representados por quatro peças que estão na base da cultura ocidental, as tragédias: Prometeu Acorrentado, Medéia , Édipo Rei e a comédia As Nuvens. A tradução, diretamente do grego, foi feita pelo renomado Mário da Gama Kury. Essa edição oferece um vasto material de apoio, que facilitará a leitura mesmo daqueles que nunca tiveram contato com as grandes peças teatrais, como textos introdutórios, resumo da ação em cada peça, perfis dos personagens, glossário e mais de 190 notas.<br><br>")
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
	
	@Schema(description = "Ano lançamento do livro", example = "2013")
	@Min(1900)
	@Max(2100)
	private String anoPublicacao;

	@Schema(description = "Quantidade de páginas do livro", example = "392")
	@NotNull()
	@Digits(integer = 4, fraction = 0)
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
