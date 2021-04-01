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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.entity.Genero;
import net.guilhermejr.apilivros.model.repository.GeneroRepository;
import net.guilhermejr.apilivros.utils.LeJSON;
import net.guilhermejr.apilivros.utils.LimpaBancoDeDados;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GeneroControllerTest {
	
	@Autowired
	private GeneroRepository generoRepository;
	
	@Autowired
	private LimpaBancoDeDados limpaBancoDeDados;

	@Autowired
	private MockMvc mockMvc;

	private String descricao1 = "Comédia";
	private String descricao2 = "Terror";
	private String descricao3 = "Fantasia";

	@BeforeEach
	private void initEach() {
		
		Genero generoCadastro1 = Genero.builder().descricao(this.descricao1).build();
		Genero generoCadastro2 = Genero.builder().descricao(this.descricao2).build();
		Genero generoCadastro3 = Genero.builder().descricao(this.descricao3).build();
		this.generoRepository.saveAll(Arrays.asList(generoCadastro1, generoCadastro2, generoCadastro3));
		
	}
	
	@AfterEach
	public void cleanUpEach() {
		this.limpaBancoDeDados.apagaTabelas();
	}
	
	@Test
	@DisplayName("Deve cadastrar um genero")
	public void deveCadastrarUmGenero() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/genero")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/genero/genero.json")))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Drama"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um genero que ja existe")
	public void deveDarErroAoCadastrarUmGeneroQueJaExiste() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/genero")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/genero/genero.json")))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Drama"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/genero")
				.contentType("application/json")
		        .content(LeJSON.conteudo("/json/correto/genero/genero.json")))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Gênero já está cadastrado."))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um genero sem descricao")
	public void deveDarErroAoCadastrarUmGeneroSemDescricao() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/genero")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/genero/genero1.json")))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Gênero deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um genero com descricao em branco")
	public void deveDarErroAoCadastrarUmGeneroComDescricaoEmBranco() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/genero")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/genero/genero2.json")))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Gênero deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um genero com muitos caracteres")
	public void deveDarErroAoCadastrarUmGeneroComMuitosCaracteres() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/genero")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/genero/genero3.json")))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Gênero deve ter no máximo 255 caracteres."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar um genero")
	public void deveRetornarUmGenero() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/genero/1"))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(this.descricao1))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar retornar um genero inexistente")
	public void deveRetornarErroAoTentarRetornarUmGeneroInexistente() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/genero/10"))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Gênero 10 não encontrado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

	@Test
	@DisplayName("Deve retornar lista de generos")
	public void deveRetornarListaDeGeneros() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/genero"))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value(this.descricao1))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].descricao").value(this.descricao3))
			.andExpect(MockMvcResultMatchers.jsonPath("$[2].descricao").value(this.descricao2))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

}
