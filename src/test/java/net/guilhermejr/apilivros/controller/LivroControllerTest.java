package net.guilhermejr.apilivros.controller;

import static org.hamcrest.CoreMatchers.hasItems;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.entity.Editora;
import net.guilhermejr.apilivros.model.entity.Estante;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.entity.Idioma;
import net.guilhermejr.apilivros.model.entity.Livro;
import net.guilhermejr.apilivros.model.entity.Tipo;
import net.guilhermejr.apilivros.model.repository.AutorRepository;
import net.guilhermejr.apilivros.model.repository.EditoraRepository;
import net.guilhermejr.apilivros.model.repository.GeneroRepository;
import net.guilhermejr.apilivros.model.repository.IdiomaRepository;
import net.guilhermejr.apilivros.model.repository.LivroRepository;
import net.guilhermejr.apilivros.utils.LeJSON;
import net.guilhermejr.apilivros.utils.LimpaBancoDeDados;
import net.guilhermejr.apilivros.utils.Token;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class LivroControllerTest {
	
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
	
	@Autowired
	private LimpaBancoDeDados limpaBancoDeDados;

	@Autowired
	private Token token;
	
	@Autowired
	private MockMvc mockMvc;
	
	private String bearerToken;

	@BeforeEach
	private void initEach() {
		
		Autor autor = Autor.builder().descricao("Sófocles").build();
		Autor AutorRetorno = this.autorRepository.save(autor);
		
		Editora editora = Editora.builder().descricao("Zahar").build();
		Editora editoraRetorno = this.editoraRepository.save(editora);
		
		Idioma idioma = Idioma.builder().descricao("português").build();
		Idioma idiomaRetorno = this.idiomaRepository.save(idioma);
		
		Genero genero = Genero.builder().descricao("Fantasia").build();
		Genero generoRetorno = this.generoRepository.save(genero);
		
		Livro livro1 = Livro.builder()
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
	
		Livro livro2 = Livro.builder()
			.anoPublicacao(2009)
			.capa("https://cache.skoob.com.br/local/images//70rhjKcZ--99JkahQFAi34PGOY0=/200x/center/top/smart/filters:format(jpeg)/https://skoob.s3.amazonaws.com/livros/7700/DIPO_REI_1362061401B.jpg")
			.descricao("Ideal para quem deseja se familiarizar com o teatro clássico, esse livro reúne os seus autores mais importantes, Aristófanes, Ésquilo, Eurípides e Sófocles, representados por quatro peças que estão na base da cultura ocidental, as tragédias: Prometeu Acorrentado, Medéia , Édipo Rei e a comédia As Nuvens. A tradução, diretamente do grego, foi feita pelo renomado Mário da Gama Kury. Essa edição oferece um vasto material de apoio, que facilitará a leitura mesmo daqueles que nunca tiveram contato com as grandes peças teatrais, como textos introdutórios, resumo da ação em cada peça, perfis dos personagens, glossário e mais de 190 notas.<br><br>")
			.extensao("jpg")
			.isbn("9788525409140")
			.paginas(104)
			.titulo("Édipo Rei")
			.ativo(Boolean.FALSE)
			.autores(List.of(AutorRetorno))
			.generos(List.of(generoRetorno))
			.editora(editoraRetorno)
			.idioma(idiomaRetorno)
			.estante(Estante.builder().id(1L).build())
			.tipo(Tipo.builder().id(1L).build())
			.build();
		
		Livro livro3 = Livro.builder()
			.anoPublicacao(1999)
			.capa("https://cache.skoob.com.br/local/images//ARsfbz6wCMsxqe47xe-Ez90E42c=/200x/center/top/smart/filters:format(jpeg)/https://skoob.s3.amazonaws.com/livros/4754/ANTIGONA_15367593714754SK1536759371B.jpg")
			.descricao("Ideal para quem deseja se familiarizar com o teatro clássico, esse livro reúne os seus autores mais importantes, Aristófanes, Ésquilo, Eurípides e Sófocles, representados por quatro peças que estão na base da cultura ocidental, as tragédias: Prometeu Acorrentado, Medéia , Édipo Rei e a comédia As Nuvens. A tradução, diretamente do grego, foi feita pelo renomado Mário da Gama Kury. Essa edição oferece um vasto material de apoio, que facilitará a leitura mesmo daqueles que nunca tiveram contato com as grandes peças teatrais, como textos introdutórios, resumo da ação em cada peça, perfis dos personagens, glossário e mais de 190 notas.<br><br>")
			.extensao("jpg")
			.isbn("9788525410092")
			.paginas(392)
			.titulo("Antígona")
			.ativo(Boolean.TRUE)
			.autores(List.of(AutorRetorno))
			.generos(List.of(generoRetorno))
			.editora(editoraRetorno)
			.idioma(idiomaRetorno)
			.estante(Estante.builder().id(1L).build())
			.tipo(Tipo.builder().id(1L).build())
			.build();
		
		this.livroRepository.saveAll(Arrays.asList(livro1, livro2, livro3));
		
		this.bearerToken = "Bearer "+ this.token.gerar();
		
	}
	
	@AfterEach
	public void cleanUpEach() {
		this.limpaBancoDeDados.apagaTabelas();
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um livro sem token")
	public void deveDarErroAoCadastrarUmLivroSemToken() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/livros")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/livro/livro.json")))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	@Test
	@DisplayName("Deve cadastrar um livro")
	public void deveCadastrarUmAutor() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/livros")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/livro/livro.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.capa").value("https://cache.skoob.com.br/local/images//MMez1YzXxUkKc8dmEdjAVQeSRgM=/200x/center/top/smart/filters:format(jpeg)/https://skoob.s3.amazonaws.com/livros/11819145/A_FILHA_DE_AUSCHWITZ_161437652011819145SK-V11614376521B.jpg"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.extensao").value("jpg"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("A filha de Auschwitz"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.subtitulo").value("Inpirado em uma história real"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("9786586553482"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Em 1942, Eva Adami embarca em um trem para Auschwitz."))
			.andExpect(MockMvcResultMatchers.jsonPath("$.editora.id").value("2"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.editora.descricao").value("Gutenberg"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.idioma.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.idioma.descricao").value("português"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.anoPublicacao").value("2021"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.paginas").value("272"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.ativo").value("true"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.autores[0].id").value("2"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.autores[0].descricao").value("Lily Graham"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[0].id").value("2"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[0].descricao").value("Drama"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[1].id").value("3"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[1].descricao").value("Ficção"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[2].id").value("4"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[2].descricao").value("Literatura Estrangeira"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[3].id").value("5"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[3].descricao").value("Romance"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.tipo.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.tipo.descricao").value("Físico"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.estante.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.estante.descricao").value("Biblioteca"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um livro com Content-Type errado")
	public void deveDarErroAoCadastrarUmLivroComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/livros")
	        .content(LeJSON.conteudo("/json/correto/livro/livro.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um livro que ja existe")
	public void deveDarErroAoCadastrarUmLivroQueJaExiste() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/livros")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/livro/livro.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/livros")
				.contentType("application/json")
		        .content(LeJSON.conteudo("/json/correto/livro/livro.json"))
		        .header("Authorization", this.bearerToken))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("isbn13"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("ISBN já está cadastrado."))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um livro sem descricao")
	public void deveDarErroAoCadastrarUmLivroSemDescricao() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/livros")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/livro/livro1.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("capa", "anoPublicacao", "paginas", "autores", "idioma", "generos", "descricao", "isbn13", "titulo", "editora", "extensao")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Capa deve ser preenchido.", "Ano deve ser maior que 1900.", "Páginas deve ser preenchido com um número inteiro.", "Autor(es) deve ser preenchido.", "Idioma deve ser preenchido.", "Ano deve ser menor que 2100.", "Gênero(s) deve ser preenchido.", "Descrição deve ser preenchido.", "ISBN deve ser preenchido.", "Título deve ser preenchido.", "ISBN deve ter 13 caracteres.", "Editora deve ser preenchido.", "Extensão deve ser preenchido.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um livro com descricao em branco")
	public void deveDarErroAoCadastrarUmLivroComDescricaoEmBranco() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/livros")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/livro/livro2.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("capa", "autores", "idioma", "generos", "descricao", "isbn13", "titulo", "editora", "extensao")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Capa deve ser preenchido.", "Autor(es) deve ser preenchido.", "Idioma deve ser preenchido.", "Gênero(s) deve ser preenchido.", "Descrição deve ser preenchido.", "ISBN deve ser preenchido.", "Título deve ser preenchido.", "Editora deve ser preenchido.", "Extensão deve ser preenchido.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um livro com muitos caracteres")
	public void deveDarErroAoCadastrarUmLivroComMuitosCaracteres() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/livros")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/livro/livro3.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("editora", "capa", "isbn13", "descricao", "idioma", "autores", "generos", "subtitulo", "extensao", "titulo")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Editora deve ser preenchido.", "Capa deve ter no máximo 255 caracteres.", "ISBN deve ser preenchido.", "Descrição deve ser preenchido.", "Idioma deve ser preenchido.", "Autor(es) deve ser preenchido.", "Gênero(s) deve ser preenchido.", "Extensão deve ter no máximo 255 caracteres.", "Título deve ter no máximo 255 caracteres.", "Subtítulo deve ter no máximo 255 caracteres.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar um livro")
	public void deveRetornarUmLivro() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/livros/1").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.capa").value("https://cache.skoob.com.br/local/images//OHxOGyXGMt9cS8pxQtUVxOzsZzo=/200x/center/top/smart/filters:format(jpeg)/https://skoob.s3.amazonaws.com/livros/335732/O_MELHOR_DO_TEATRO_GREGO_1386085857B.jpg"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.extensao").value("jpg"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.titulo").value("O Melhor do Teatro Grego"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.isbn").value("9788537810743"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Ideal para quem deseja se familiarizar com o teatro clássico, esse livro reúne os seus autores mais importantes, Aristófanes, Ésquilo, Eurípides e Sófocles, representados por quatro peças que estão na base da cultura ocidental, as tragédias: Prometeu Acorrentado, Medéia , Édipo Rei e a comédia As Nuvens. A tradução, diretamente do grego, foi feita pelo renomado Mário da Gama Kury. Essa edição oferece um vasto material de apoio, que facilitará a leitura mesmo daqueles que nunca tiveram contato com as grandes peças teatrais, como textos introdutórios, resumo da ação em cada peça, perfis dos personagens, glossário e mais de 190 notas.<br><br>"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.editora.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.editora.descricao").value("Zahar"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.idioma.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.idioma.descricao").value("português"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.paginas").value("392"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.ativo").value("true"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.autores[0].id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.autores[0].descricao").value("Sófocles"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[0].id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.generos[0].descricao").value("Fantasia"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.tipo.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.tipo.descricao").value("Físico"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.estante.id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.estante.descricao").value("Biblioteca"))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar retornar um livro inexistente")
	public void deveRetornarErroAoTentarRetornarUmLivroInexistente() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/livros/10").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Livro 10 não encontrado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

	@Test
	@DisplayName("Deve retornar lista de livros")
	public void deveRetornarListaDeLivros() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/livros?estante=1").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value("3"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[0].titulo").value("Antígona"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].id").value("2"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[1].titulo").value("Édipo Rei"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[2].id").value("1"))
			.andExpect(MockMvcResultMatchers.jsonPath("$.content[2].titulo").value("O Melhor do Teatro Grego"))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve mudar livro de estante")
	public void deveMudarLivroDeEstante() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put("/livros/1/estante/2").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar mudar livro inexistente de estante")
	public void deveRetornarErroAoTentarMudarLivroInexistenteDeEstante() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/livros/10/estante/1").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Livro 10 não encontrado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar mudar livro de estante inexistente")
	public void deveRetornarErroAoTentarMudarLivroDeEstanteInexistente() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/livros/1/estante/10").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Estante 10 não encontrada."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao mudar livro de estante com Content-Type errado")
	public void deveDarErroAoMudarLivroDeEstanteComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/livros/1/estante/2").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve ativar livro")
	public void deveAtivarLivro() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put("/livros/2/ativar").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar ativar livro já ativado")
	public void deveDarErroAoTentarAtivarLivroJaAtivado() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put("/livros/1/ativar").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Livro 1 já está ativo."));
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar ativar livro inexistente")
	public void deveDarErroAoTentarAtivarLivroInexistente() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put("/livros/10/ativar").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Livro 10 não encontrado."));
		
	}
	
	@Test
	@DisplayName("Deve dar erro ativar um livro com Content-Type errado")
	public void deveDarErroAoAtivarUmLivroComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/livros/1/ativar").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve deaativar livro")
	public void deveDesativarLivro() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.delete("/livros/1/desativar").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar desativar livro já desativado")
	public void deveDarErroAoTentarDesativarLivroJaDesativado() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.delete("/livros/2/desativar").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Livro 2 já está inativo."));
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar desativar livro inexistente")
	public void deveDarErroAoTentarDesativarLivroInexistente() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.delete("/livros/10/desativar").contentType("application/json").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Livro 10 não encontrado."));
		
	}
	
	@Test
	@DisplayName("Deve dar erro desativar um livro com Content-Type errado")
	public void deveDarErroAoDesativarUmLivroComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.delete("/livros/1/desativar").header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

}
