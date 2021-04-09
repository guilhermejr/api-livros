package net.guilhermejr.apilivros.controller;

import java.util.Arrays;

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

import net.guilhermejr.apilivros.model.entity.Idioma;
import net.guilhermejr.apilivros.model.repository.IdiomaRepository;
import net.guilhermejr.apilivros.utils.LeJSON;
import net.guilhermejr.apilivros.utils.LimpaBancoDeDados;
import net.guilhermejr.apilivros.utils.Token;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IdiomaControllerTest {
	
	@Autowired
	private IdiomaRepository idiomaRepository;
	
	@Autowired
	private LimpaBancoDeDados limpaBancoDeDados;

	@Autowired
	private Token token;
	
	@Autowired
	private MockMvc mockMvc;

	private String descricao1 = "Português";
	private String descricao2 = "Árabe";
	private String descricao3 = "Japonês";
	
	private String bearerToken;

	@BeforeEach
	private void initEach() {
		
		Idioma idiomaCadastro1 = Idioma.builder().descricao(this.descricao1).build();
		Idioma idiomaCadastro2 = Idioma.builder().descricao(this.descricao2).build();
		Idioma idiomaCadastro3 = Idioma.builder().descricao(this.descricao3).build();
		this.idiomaRepository.saveAll(Arrays.asList(idiomaCadastro1, idiomaCadastro2, idiomaCadastro3));
		
		this.bearerToken = "Bearer "+ this.token.gerar();
		
	}
	
	@AfterEach
	public void cleanUpEach() {
		this.limpaBancoDeDados.apagaTabelas();
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um idioma sem token")
	public void deveDarErroAoCadastrarUmIdiomaSemToken() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/idiomas")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/idioma/idioma.json")))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	@Test
	@DisplayName("Deve cadastrar um idioma")
	public void deveCadastrarUmIdioma() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/idiomas")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/idioma/idioma.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Alemão"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um idioma com Content-Type errado")
	public void deveDarErroAoCadastrarUmIdiomaComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/idiomas")
	        .content(LeJSON.conteudo("/json/correto/idioma/idioma.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um idioma que ja existe")
	public void deveDarErroAoCadastrarUmIdiomaQueJaExiste() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/idiomas")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/idioma/idioma.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Alemão"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/idiomas")
				.contentType("application/json")
		        .content(LeJSON.conteudo("/json/correto/idioma/idioma.json"))
		        .header("Authorization", this.bearerToken))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Idioma já está cadastrado."))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um idioma sem descricao")
	public void deveDarErroAoCadastrarUmIdiomaSemDescricao() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/idiomas")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/idioma/idioma1.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Idioma deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um idioma com descricao em branco")
	public void deveDarErroAoCadastrarUmIdiomaComDescricaoEmBranco() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/idiomas")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/idioma/idioma2.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Idioma deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um idioma com muitos caracteres")
	public void deveDarErroAoCadastrarUmIdiomaComMuitosCaracteres() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/idiomas")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/idioma/idioma3.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Idioma deve ter no máximo 255 caracteres."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar um genero")
	public void deveRetornarUmIdioma() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/idiomas/1").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(this.descricao1))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao retornar um idioma com Content-Type errado")
	public void deveDarErroAoRetornarUmIdiomaComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/idiomas/1"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar retornar um genero inexistente")
	public void deveRetornarErroAoTentarRetornarUmIdiomaInexistente() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/idiomas/10").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Idioma 10 não encontrado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

	@Test
	@DisplayName("Deve retornar lista de generos")
	public void deveRetornarListaDeIdiomas() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/idiomas").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value(this.descricao2))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].descricao").value(this.descricao3))
			.andExpect(MockMvcResultMatchers.jsonPath("$[2].descricao").value(this.descricao1))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao listar idiomas com Content-Type errado")
	public void deveDarErroAoListarIdiomasComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/idiomas"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

}
