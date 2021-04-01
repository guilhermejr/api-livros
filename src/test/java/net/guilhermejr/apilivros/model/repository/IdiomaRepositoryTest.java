package net.guilhermejr.apilivros.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.guilhermejr.apilivros.model.entity.Idioma;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class IdiomaRepositoryTest {
	
	@Autowired
	private IdiomaRepository idiomaRepository;
	
	private String descricao1 = "Português";
	private String descricao2 = "Inglês";
	private String descricao3 = "Alemão";
	
	@BeforeEach
	private void criaIdiomas() {
		
		Idioma idiomaCadastro1 = Idioma.builder().descricao(this.descricao1).build();
		Idioma idiomaCadastro2 = Idioma.builder().descricao(this.descricao2).build();
		Idioma idiomaCadastro3 = Idioma.builder().descricao(this.descricao3).build();
		this.idiomaRepository.saveAll(Arrays.asList(idiomaCadastro1, idiomaCadastro2, idiomaCadastro3));
		
	}

	@Test
	@DisplayName("Busca idioma por descrição")
	public void BuscaIdiomaPorDescricao() {
		
		Idioma idioma = this.idiomaRepository.findByDescricao(this.descricao2);
		assertNotNull(idioma);
		assertEquals(this.descricao2, idioma.getDescricao());
		
	}
	
	@Test
	@DisplayName("Retorna idioma ordenado por descrição")
	public void RetornaIdiomaOrdenadoPorDescricao() {
		
		List<Idioma> idiomas = this.idiomaRepository.findByOrderByDescricao();
		assertEquals(this.descricao3, idiomas.get(0).getDescricao());
		assertEquals(this.descricao2, idiomas.get(1).getDescricao());
		assertEquals(this.descricao1, idiomas.get(2).getDescricao());
		
	}
	
	@Test
	@DisplayName("Informa se idioma já está cadastrado")
	public void InformaSeIdiomaJaEstaCadastrada() {
		
		boolean retorno1 = this.idiomaRepository.existsByDescricao(this.descricao1);
		boolean retorno2 = this.idiomaRepository.existsByDescricao("Espanhol");
		
		assertTrue(retorno1);
		assertFalse(retorno2);
		
	}

}
