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
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import net.guilhermejr.apilivros.model.entity.Estante;

@DataJpaTest
public class EstanteRepositoryTest {
	
	@Autowired
	private EstanteRepository estanteRepository;
	
	private String descricao1 = "Biblioteca";
	private String descricao2 = "Desejados";
	private String descricao3 = "Troca";
	
	@BeforeEach
	private void criaEstantes() {
		
		Estante estanteCadastro1 = Estante.builder().descricao(this.descricao1).build();
		Estante estanteCadastro2 = Estante.builder().descricao(this.descricao2).build();
		Estante estanteCadastro3 = Estante.builder().descricao(this.descricao3).build();
		this.estanteRepository.saveAll(Arrays.asList(estanteCadastro1, estanteCadastro2, estanteCadastro3));
		
	}

	@Test
	@DisplayName("Busca estante por descrição")
	public void BuscaEstantePorDescricao() {
		
		Estante estante = this.estanteRepository.findByDescricao(this.descricao2);
		assertNotNull(estante);
		assertEquals(this.descricao2, estante.getDescricao());
		
	}
	
	@Test
	@DisplayName("Retorna estante ordenado por descrição")
	public void RetornaEstanteOrdenadoPorDescricao() {
		
		List<Estante> estantes = this.estanteRepository.findByOrderByDescricao();
		assertEquals(this.descricao1, estantes.get(0).getDescricao());
		assertEquals(this.descricao2, estantes.get(1).getDescricao());
		assertEquals(this.descricao3, estantes.get(2).getDescricao());
		
	}
	
	@Test
	@DisplayName("Informa se estante já está cadastrada")
	public void InformaSeEstanteJaEstaCadastrada() {
		
		boolean retorno1 = this.estanteRepository.existsByDescricao(this.descricao1);
		boolean retorno2 = this.estanteRepository.existsByDescricao("Doação");
		
		assertTrue(retorno1);
		assertFalse(retorno2);
		
	}

}
