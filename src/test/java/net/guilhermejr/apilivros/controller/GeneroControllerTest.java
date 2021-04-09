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

import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.repository.GeneroRepository;
import net.guilhermejr.apilivros.utils.LeJSON;
import net.guilhermejr.apilivros.utils.LimpaBancoDeDados;
import net.guilhermejr.apilivros.utils.Token;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GeneroControllerTest {
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private LimpaBancoDeDados limpaBancoDeDados;

	@Autowired
	private Token token;
	
	@Autowired
	private MockMvc mockMvc;

	private String descricao1 = "Comédia";
	private String descricao2 = "Terror";
	private String descricao3 = "Fantasia";
	
	private String bearerToken;

	@BeforeEach
	private void initEach() {
		
		Genero generoCadastro1 = Genero.builder().descricao(this.descricao1).build();
		Genero generoCadastro2 = Genero.builder().descricao(this.descricao2).build();
		Genero generoCadastro3 = Genero.builder().descricao(this.descricao3).build();
		this.generoRepository.saveAll(Arrays.asList(generoCadastro1, generoCadastro2, generoCadastro3));
		
		this.bearerToken = "Bearer "+ this.token.gerar();
		
	}
	
	@AfterEach
	public void cleanUpEach() {
		this.limpaBancoDeDados.apagaTabelas();
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um gênero sem token")
	public void deveDarErroAoCadastrarUmGeneroSemToken() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/generos")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/genero/genero.json")))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	@Test
	@DisplayName("Deve cadastrar um gênero")
	public void deveCadastrarUmGenero() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/generos")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/genero/genero.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Drama"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um gênero com Content-Type errado")
	public void deveDarErroAoCadastrarUmGeneroComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/generos")
	        .content(LeJSON.conteudo("/json/correto/genero/genero.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um gênero que ja existe")
	public void deveDarErroAoCadastrarUmGeneroQueJaExiste() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/generos")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/genero/genero.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Drama"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/generos")
				.contentType("application/json")
		        .content(LeJSON.conteudo("/json/correto/genero/genero.json"))
		        .header("Authorization", this.bearerToken))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Gênero já está cadastrado."))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um gênero sem descricao")
	public void deveDarErroAoCadastrarUmGeneroSemDescricao() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/generos")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/genero/genero1.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Gênero deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um gênero com descricao em branco")
	public void deveDarErroAoCadastrarUmGeneroComDescricaoEmBranco() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/generos")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/genero/genero2.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Gênero deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um gênero com muitos caracteres")
	public void deveDarErroAoCadastrarUmGeneroComMuitosCaracteres() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/generos")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/genero/genero3.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Gênero deve ter no máximo 255 caracteres."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar um gênero")
	public void deveRetornarUmGenero() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/generos/1").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(this.descricao1))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao retornar um gênero com Content-Type errado")
	public void deveDarErroAoRetornarUmGeneroComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/generos/1"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar retornar um gênero inexistente")
	public void deveRetornarErroAoTentarRetornarUmGeneroInexistente() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/generos/10").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Gênero 10 não encontrado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

	@Test
	@DisplayName("Deve retornar lista de gênero")
	public void deveRetornarListaDeGeneros() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/generos").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value(this.descricao1))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].descricao").value(this.descricao3))
			.andExpect(MockMvcResultMatchers.jsonPath("$[2].descricao").value(this.descricao2))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao listar gêneros com Content-Type errado")
	public void deveDarErroAoListarGenerosComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/generos"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

}
