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

import net.guilhermejr.apilivros.model.entity.Genero;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class GeneroRepositoryTest {
	
	@Autowired
	private GeneroRepository generoRepository;
	
	private String descricao1 = "Romance";
	private String descricao2 = "Drama";
	private String descricao3 = "Comédia";
	
	@BeforeEach
	private void criaGeneros() {
		
		Genero generoCadastro1 = Genero.builder().descricao(this.descricao1).build();
		Genero generoCadastro2 = Genero.builder().descricao(this.descricao2).build();
		Genero generoCadastro3 = Genero.builder().descricao(this.descricao3).build();
		this.generoRepository.saveAll(Arrays.asList(generoCadastro1, generoCadastro2, generoCadastro3));
		
	}

	@Test
	@DisplayName("Busca gênero por descrição")
	public void BuscaGeneroPorDescricao() {
		
		Genero genero = this.generoRepository.findByDescricao(this.descricao2);
		assertNotNull(genero);
		assertEquals(this.descricao2, genero.getDescricao());
		
	}
	
	@Test
	@DisplayName("Retorna gênero ordenado por descrição")
	public void RetornaGeneroOrdenadoPorDescricao() {
		
		List<Genero> generos = this.generoRepository.findByOrderByDescricao();
		assertEquals(this.descricao3, generos.get(0).getDescricao());
		assertEquals(this.descricao2, generos.get(1).getDescricao());
		assertEquals(this.descricao1, generos.get(2).getDescricao());
		
	}
	
	@Test
	@DisplayName("Informa se gênero já está cadastrado")
	public void InformaSeGeneroJaEstaCadastrada() {
		
		boolean retorno1 = this.generoRepository.existsByDescricao(this.descricao1);
		boolean retorno2 = this.generoRepository.existsByDescricao("Ficção científica");
		
		assertTrue(retorno1);
		assertFalse(retorno2);
		
	}

}
