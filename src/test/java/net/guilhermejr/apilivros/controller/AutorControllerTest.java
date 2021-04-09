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

import net.guilhermejr.apilivros.model.entity.Autor;
import net.guilhermejr.apilivros.model.repository.AutorRepository;
import net.guilhermejr.apilivros.utils.LeJSON;
import net.guilhermejr.apilivros.utils.LimpaBancoDeDados;
import net.guilhermejr.apilivros.utils.Token;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AutorControllerTest {
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LimpaBancoDeDados limpaBancoDeDados;
	
	@Autowired
	private Token token;

	@Autowired
	private MockMvc mockMvc;

	private String descricao1 = "Camila Oliveira";
	private String descricao2 = "Guilherme Oliveira";
	private String descricao3 = "Amanda Alves";
	
	private String bearerToken;

	@BeforeEach
	private void initEach() {
		
		Autor autorCadastro1 = Autor.builder().descricao(this.descricao1).build();
		Autor autorCadastro2 = Autor.builder().descricao(this.descricao2).build();
		Autor autorCadastro3 = Autor.builder().descricao(this.descricao3).build();
		this.autorRepository.saveAll(Arrays.asList(autorCadastro1, autorCadastro2, autorCadastro3));
		
		this.bearerToken = "Bearer "+ this.token.gerar();
		
	}
	
	@AfterEach
	public void cleanUpEach() {
		this.limpaBancoDeDados.apagaTabelas();
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um autor sem token")
	public void deveDarErroAoCadastrarUmAutorSemToken() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/autores")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/autor/autor.json")))
			//.andDo(MockMvcResultHandlers.print())
			.andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	@Test
	@DisplayName("Deve cadastrar um autor")
	public void deveCadastrarUmAutor() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/autores")
			.contentType("application/json")
			.header("Authorization", this.bearerToken)
	        .content(LeJSON.conteudo("/json/correto/autor/autor.json")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Paulo Coelho"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um autor com Content-Type errado")
	public void deveDarErroAoCadastrarUmAutorComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/autores")
	        .content(LeJSON.conteudo("/json/correto/autor/autor.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um autor que ja existe")
	public void deveDarErroAoCadastrarUmAutorQueJaExiste() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/autores")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/autor/autor.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Paulo Coelho"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/autores")
				.contentType("application/json")
		        .content(LeJSON.conteudo("/json/correto/autor/autor.json"))
		        .header("Authorization", this.bearerToken))
				//.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Autor já está cadastrado."))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um autor sem descricao")
	public void deveDarErroAoCadastrarUmAutorSemDescricao() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/autores")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/autor/autor1.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Autor deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um autor com descricao em branco")
	public void deveDarErroAoCadastrarUmAutorComDescricaoEmBranco() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/autores")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/autor/autor2.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Autor deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar um autor com muitos caracteres")
	public void deveDarErroAoCadastrarUmAutorComMuitosCaracteres() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/autores")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/autor/autor3.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Autor deve ter no máximo 255 caracteres."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar um autor")
	public void deveRetornarUmAutor() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/autores/1").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(this.descricao1))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao retornar um autor com Content-Type errado")
	public void deveDarErroAoRetornarUmAutorComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/autores/1"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar retornar um autor inexistente")
	public void deveRetornarErroAoTentarRetornarUmAutorInexistente() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/autores/10").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Autor 10 não encontrado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

	@Test
	@DisplayName("Deve retornar lista de autores")
	public void deveRetornarListaDeAutores() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/autores").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value(this.descricao3))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].descricao").value(this.descricao1))
			.andExpect(MockMvcResultMatchers.jsonPath("$[2].descricao").value(this.descricao2))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao listar autores com Content-Type errado")
	public void deveDarErroAoListarAutoresComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/autores"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

}
