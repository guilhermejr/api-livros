package net.guilhermejr.apilivros.controller;

import static org.hamcrest.CoreMatchers.hasItems;

import org.junit.jupiter.api.Assertions;
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

import net.guilhermejr.apilivros.utils.LeJSON;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AutenticacaoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	@DisplayName("Deve efeturar login")
	public void deveEfetuarLogin() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/auth")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/autenticacao/autenticacao.json")))
			.andExpect(MockMvcResultMatchers.jsonPath("$.access_token").exists())
			.andExpect(MockMvcResultMatchers.jsonPath("$.token_type").value("Bearer"))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar logar com Content-Type errado")
	public void deveDarErroAoTentarLogarComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/auth")
	        .content(LeJSON.conteudo("/json/correto/autenticacao/autenticacao.json")))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar efetuar login sem e-mail e senha")
	public void deveDarErroAoTentarEfetuarLoginSemEmailESenha() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/auth")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/autenticacao/autenticacao1.json")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("email", "senha")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("E-mail deve ser preenchido.", "Senha deve ser preenchido.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar efetuar login com e-mail inválido")
	public void deveDarErroAoTentarEfetuarLoginComEmailInvalido() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/auth")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/autenticacao/autenticacao2.json")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("email"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("E-mail está inválido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar efetuar login sem senha")
	public void deveDarErroAoTentarEfetuarLoginSemSenha() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/auth")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/autenticacao/autenticacao3.json")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("senha"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("Senha deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao tentar efetuar login sem e-mail")
	public void deveDarErroAoTentarEfetuarLoginSemEmail() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.post("/auth")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/incorreto/autenticacao/autenticacao4.json")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].campo").value("email"))
			.andExpect(MockMvcResultMatchers.jsonPath("$[0].mensagem").value("E-mail deve ser preenchido."))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

}
