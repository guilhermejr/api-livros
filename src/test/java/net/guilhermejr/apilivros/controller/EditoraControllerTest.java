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

import net.guilhermejr.apilivros.model.entity.Editora;
import net.guilhermejr.apilivros.model.repository.EditoraRepository;
import net.guilhermejr.apilivros.utils.LeJSON;
import net.guilhermejr.apilivros.utils.LimpaBancoDeDados;
import net.guilhermejr.apilivros.utils.Token;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EditoraControllerTest {
	
	@Autowired
	private EditoraRepository editoraRepository;
	
	@Autowired
	private LimpaBancoDeDados limpaBancoDeDados;

	@Autowired
	private Token token;
	
	@Autowired
	private MockMvc mockMvc;

	private String descricao1 = "Rocco";
	private String descricao2 = "Harper Collins";
	private String descricao3 = "Abril";
	
	private String bearerToken;

	@BeforeEach
	private void initEach() {
		
		Editora editoraCadastro1 = Editora.builder().descricao(this.descricao1).build();
		Editora editoraCadastro2 = Editora.builder().descricao(this.descricao2).build();
		Editora editoraCadastro3 = Editora.builder().descricao(this.descricao3).build();
		this.editoraRepository.saveAll(Arrays.asList(editoraCadastro1, editoraCadastro2, editoraCadastro3));
		
		this.bearerToken = "Bearer "+ this.token.gerar();
		
	}
	
	@AfterEach
	public void cleanUpEach() {
		this.limpaBancoDeDados.apagaTabelas();
	}

	@Test
	@DisplayName("Deve dar erro ao cadastrar uma editora sem token")
	public void deveDarErroAoCadastrarUmaEditoraSemToken() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.post("/editoras")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/editora/editora.json")))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	@Test
	@DisplayName("Deve cadastrar uma editora")
	public void deveCadastrarUmaEditora() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/editoras")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/editora/editora.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Darkside"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar uma editora com Content-Type errado")
	public void deveDarErroAoCadastrarUmaEditoraComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/editoras")
	        .content(LeJSON.conteudo("/json/correto/editora/editora.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar uma editora que ja existe")
	public void deveDarErroAoCadastrarUmaEditoraQueJaExiste() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/editoras")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/editora/editora.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value("Darkside"))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		mvcResult = this.mockMvc
				.perform(MockMvcRequestBuilders.post("/editoras")
				.contentType("application/json")
		        .content(LeJSON.conteudo("/json/correto/editora/editora.json"))
		        .header("Authorization", this.bearerToken))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Editora já está cadastrada."))
				.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar uma editora sem descricao")
	public void deveDarErroAoCadastrarUmaEditoraSemDescricao() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/editoras")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/editora/editora1.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Editora deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar uma editora com descricao em branco")
	public void deveDarErroAoCadastrarUmaEditoraComDescricaoEmBranco() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/editoras")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/editora/editora2.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Editora deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao cadastrar uma editora com muitos caracteres")
	public void deveDarErroAoCadastrarUmaEditoraComMuitosCaracteres() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/editoras")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/editora/editora3.json"))
	        .header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("descricao"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Editora deve ter no máximo 255 caracteres."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar uma editora")
	public void deveRetornarUmaEditora() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/editoras/1").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$.descricao").value(this.descricao1))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao retornar uma editora com Content-Type errado")
	public void deveDarErroAoRetornarUmaEditoraComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/editoras/1"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve retornar erro ao tentar retornar uma editora inexistente")
	public void deveRetornarErroAoTentarRetornarUmaEditoraInexistente() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/editoras/10").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isNotFound())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(404))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Editora 10 não encontrada."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

	@Test
	@DisplayName("Deve retornar lista de editoras")
	public void deveRetornarListaDeEditoras() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/editoras").contentType("application/json"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].descricao").value(this.descricao3))
			.andExpect(MockMvcResultMatchers.jsonPath("$[1].descricao").value(this.descricao2))
			.andExpect(MockMvcResultMatchers.jsonPath("$[2].descricao").value(this.descricao1))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao listar editoras com Content-Type errado")
	public void deveDarErroAoListarEditorasComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.get("/editoras"))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

}
