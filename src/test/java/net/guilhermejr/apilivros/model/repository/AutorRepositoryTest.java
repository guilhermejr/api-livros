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

import net.guilhermejr.apilivros.model.entity.Autor;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AutorRepositoryTest {
	
	@Autowired
	private AutorRepository autorRepository;
	
	private String descricao1 = "Camila Oliveira";
	private String descricao2 = "Guilherme Oliveira";
	private String descricao3 = "Amanda Alves";
	
	@BeforeEach
	private void criaAutores() {
		
		Autor autorCadastro1 = Autor.builder().descricao(this.descricao1).build();
		Autor autorCadastro2 = Autor.builder().descricao(this.descricao2).build();
		Autor autorCadastro3 = Autor.builder().descricao(this.descricao3).build();
		this.autorRepository.saveAll(Arrays.asList(autorCadastro1, autorCadastro2, autorCadastro3));
		
	}

	@Test
	@DisplayName("Busca autor por descrição")
	public void BuscaAutorPorDescricao() {
		
		Autor autor = this.autorRepository.findByDescricao(this.descricao2);
		assertNotNull(autor);
		assertEquals(this.descricao2, autor.getDescricao());
		
	}
	
	@Test
	@DisplayName("Retorna autor ordenado por descrição")
	public void RetornaAutorOrdenadoPorDescricao() {
		
		List<Autor> autores = this.autorRepository.findByOrderByDescricao();
		assertEquals(this.descricao3, autores.get(0).getDescricao());
		assertEquals(this.descricao1, autores.get(1).getDescricao());
		assertEquals(this.descricao2, autores.get(2).getDescricao());
		
	}
	
	@Test
	@DisplayName("Informa se autor já está cadastrado")
	public void InformaSeAutorJaEstaCadastrado() {
		
		boolean retorno1 = this.autorRepository.existsByDescricao(this.descricao1);
		boolean retorno2 = this.autorRepository.existsByDescricao("Alex Oliveira");
		
		assertTrue(retorno1);
		assertFalse(retorno2);
		
	}

}
