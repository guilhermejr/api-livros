package net.guilhermejr.apilivros.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;

import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.entity.Editora;
import net.guilhermejr.apilivros.model.entity.Estante;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.entity.Idioma;
import net.guilhermejr.apilivros.model.entity.Livro;
import net.guilhermejr.apilivros.model.entity.Tipo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LivroRepositoryTest {
	
	@Autowired
	private AutorRepository autorRepository;

	@Autowired
	private EditoraRepository editoraRepository;

	@Autowired
	private IdiomaRepository idiomaRepository;

	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private LivroRepository livroRepository;
	
	@BeforeEach
	private void criaLivro() {
		
		Autor autor = Autor.builder().descricao("Sófocles").build();
		Autor AutorRetorno = this.autorRepository.save(autor);
		
		Editora editora = Editora.builder().descricao("Zahar").build();
		Editora editoraRetorno = this.editoraRepository.save(editora);
		
		Idioma idioma = Idioma.builder().descricao("postuguês").build();
		Idioma idiomaRetorno = this.idiomaRepository.save(idioma);
		
		Genero genero = Genero.builder().descricao("Fantasia").build();
		Genero generoRetorno = this.generoRepository.save(genero);
		
		Livro livro = Livro.builder()
			.anoPublicacao(2013)
			.capa("https://cache.skoob.com.br/local/images//OHxOGyXGMt9cS8pxQtUVxOzsZzo=/200x/center/top/smart/filters:format(jpeg)/https://skoob.s3.amazonaws.com/livros/335732/O_MELHOR_DO_TEATRO_GREGO_1386085857B.jpg")
			.descricao("Ideal para quem deseja se familiarizar com o teatro clássico, esse livro reúne os seus autores mais importantes, Aristófanes, Ésquilo, Eurípides e Sófocles, representados por quatro peças que estão na base da cultura ocidental, as tragédias: Prometeu Acorrentado, Medéia , Édipo Rei e a comédia As Nuvens. A tradução, diretamente do grego, foi feita pelo renomado Mário da Gama Kury. Essa edição oferece um vasto material de apoio, que facilitará a leitura mesmo daqueles que nunca tiveram contato com as grandes peças teatrais, como textos introdutórios, resumo da ação em cada peça, perfis dos personagens, glossário e mais de 190 notas.<br><br>")
			.extensao("jpg")
			.isbn("9788537810743")
			.paginas(392)
			.titulo("O Melhor do Teatro Grego")
			.ativo(Boolean.TRUE)
			.autores(List.of(AutorRetorno))
			.generos(List.of(generoRetorno))
			.editora(editoraRetorno)
			.idioma(idiomaRetorno)
			.estante(Estante.builder().id(1L).build())
			.tipo(Tipo.builder().id(1L).build())
			.build();
		
		this.livroRepository.save(livro);
	}

	@Test
	@DisplayName("Busca livro por estante")
	public void BuscaLivroPorEstante() {
		
		Pageable paginacao = PageRequest.of(0, 24);
		
		Page<Livro> livro = this.livroRepository.findByEstanteId(1L, paginacao);
		
		assertNotNull(livro);
		assertEquals("O Melhor do Teatro Grego", livro.getContent().get(0).getTitulo());
		assertEquals(24, livro.getSize());
		assertEquals(0, livro.getNumber());
		assertEquals(1, livro.getTotalPages());
		assertEquals(1, livro.getTotalElements());
		
	}
	
	@Test
	@DisplayName("Informa se ISBN já está cadastrado")
	public void InformaSeISBNJaEstaCadastrado() {
		
		boolean retorno1 = this.livroRepository.existsByIsbn("9788537810743");
		boolean retorno2 = this.livroRepository.existsByIsbn("1233211233213");
		
		assertTrue(retorno1);
		assertFalse(retorno2);
		
	}

}
