package net.guilhermejr.apilivros.controller;

import static org.hamcrest.CoreMatchers.hasItems;

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

import net.guilhermejr.apilivros.utils.LeJSON;
import net.guilhermejr.apilivros.utils.LimpaBancoDeDados;
import net.guilhermejr.apilivros.utils.Token;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {
	
	@Autowired
	private LimpaBancoDeDados limpaBancoDeDados;
	
	@Autowired
	private Token token;
	
	@Autowired
	private MockMvc mockMvc;
	
	private String bearerToken;
	
	@BeforeEach
	private void initEach() {
		this.bearerToken = "Bearer "+ this.token.gerar();
	}
	
	@AfterEach
	public void cleanUpEach() {
		this.limpaBancoDeDados.apagaTabelas();
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha sem token")
	public void deveDarErroAoTrocarSenhaSemToken() throws Exception {
		
		this.mockMvc
		.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
			.contentType("application/json")
	        .content(LeJSON.conteudo("/json/correto/usuario/usuario.json")))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
		
	}
	
	@Test
	@DisplayName("Deve trocar a senha")
	public void deveTrocarASenha() throws Exception {
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/correto/usuario/usuario.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
		this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/correto/usuario/usuario1.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com Content-Type errado")
	public void deveDarErroAoTrocarSenhaComContentTypeErrado() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.content(LeJSON.conteudo("/json/correto/usuario/usuario.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.status().isUnsupportedMediaType())
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(415))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Content-Type não suportado."))
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com dados incompletos 1")
	public void deveDarErroAoTrocarSenhaComDadosIncompletos1() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario1.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("senhaNova", "senhaAtual", "senhaNovaConfirmar")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Nova senha deve ter no mínimo 8 caracteres, 1 número, 1 letra minúscula, 1 letra maiúscula e 1 caractere especial.", "Senha Atual deve ser preenchida.", "Confirmar nova senha deve ter no mínimo 8 caracteres, 1 número, 1 letra minúscula, 1 letra maiúscula e 1 caractere especial.", "Confirmar nova senha deve ser preenchida.", "Nova senha deve ser preenchida.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com dados incompletos 2")
	public void deveDarErroAoTrocarSenhaComDadosIncompletos2() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario2.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("senhaNova", "senhaNovaConfirmar")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Nova senha deve ter no mínimo 8 caracteres, 1 número, 1 letra minúscula, 1 letra maiúscula e 1 caractere especial.", "Confirmar nova senha deve ter no mínimo 8 caracteres, 1 número, 1 letra minúscula, 1 letra maiúscula e 1 caractere especial.", "Confirmar nova senha deve ser preenchida.", "Nova senha deve ser preenchida.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com dados incompletos 3")
	public void deveDarErroAoTrocarSenhaComDadosIncompletos3() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario3.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("senhaAtual", "senhaNovaConfirmar")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Senha Atual deve ser preenchida.", "Confirmar nova senha deve ter no mínimo 8 caracteres, 1 número, 1 letra minúscula, 1 letra maiúscula e 1 caractere especial.", "Confirmar nova senha deve ser preenchida.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com dados incompletos 4")
	public void deveDarErroAoTrocarSenhaComDadosIncompletos4() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario4.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("senhaNova", "senhaAtual")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Nova senha deve ter no mínimo 8 caracteres, 1 número, 1 letra minúscula, 1 letra maiúscula e 1 caractere especial.", "Senha Atual deve ser preenchida.", "Nova senha deve ser preenchida.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com dados incompletos 5")
	public void deveDarErroAoTrocarSenhaComDadosIncompletos5() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario5.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("senhaNovaConfirmar")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Confirmar nova senha deve ter no mínimo 8 caracteres, 1 número, 1 letra minúscula, 1 letra maiúscula e 1 caractere especial.", "Confirmar nova senha deve ser preenchida.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com dados incompletos 6")
	public void deveDarErroAoTrocarSenhaComDadosIncompletos6() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario6.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("senhaAtual")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Senha Atual deve ser preenchida.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com dados incompletos 7")
	public void deveDarErroAoTrocarSenhaComDadosIncompletos7() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario7.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].campo", hasItems("senhaNova")))
			.andExpect(MockMvcResultMatchers.jsonPath("$[*].mensagem", hasItems("Nova senha deve ter no mínimo 8 caracteres, 1 número, 1 letra minúscula, 1 letra maiúscula e 1 caractere especial.", "Nova senha deve ser preenchida.")))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}
	
	@Test
	@DisplayName("Deve dar erro ao trocar senha com nova senha e conformar nova senha diferentes")
	public void deveDarErroAoTrocarSenhaComNovaSenhaEConfirmarNovaSenhaDiferentes() throws Exception {
		
		MvcResult mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario8.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Nova senha e Confirmar nova senha devem ser iguais"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
		mvcResult = this.mockMvc
			.perform(MockMvcRequestBuilders.put("/usuarios/trocar-senha")
				.contentType("application/json")
				.content(LeJSON.conteudo("/json/incorreto/usuario/usuario9.json"))
				.header("Authorization", this.bearerToken))
			.andExpect(MockMvcResultMatchers.jsonPath("$.status").value(400))
			.andExpect(MockMvcResultMatchers.jsonPath("$.detalhe").value("Nova senha e Confirmar nova senha devem ser iguais"))
			.andExpect(MockMvcResultMatchers.status().isBadRequest())
			.andReturn();
		
		Assertions.assertEquals("application/json", mvcResult.getResponse().getContentType());
		
	}

}
