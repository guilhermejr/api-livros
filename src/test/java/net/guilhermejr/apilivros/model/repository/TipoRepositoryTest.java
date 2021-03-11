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

import net.guilhermejr.apilivros.model.entity.Tipo;

@DataJpaTest
public class TipoRepositoryTest {
	
	@Autowired
	private TipoRepository tipoRepository;
	
	private String descricao1 = "Físico";
	private String descricao2 = "E-book";
	private String descricao3 = "PDF";
	
	@BeforeEach
	private void criaTipos() {
		
		Tipo tipoCadastro1 = Tipo.builder().descricao(this.descricao1).build();
		Tipo tipoCadastro2 = Tipo.builder().descricao(this.descricao2).build();
		Tipo tipoCadastro3 = Tipo.builder().descricao(this.descricao3).build();
		this.tipoRepository.saveAll(Arrays.asList(tipoCadastro1, tipoCadastro2, tipoCadastro3));
		
	}

	@Test
	@DisplayName("Busca tipo por descrição")
	public void BuscaTipoPorDescricao() {
		
		Tipo tipo = this.tipoRepository.findByDescricao(this.descricao2);
		assertNotNull(tipo);
		assertEquals(this.descricao2, tipo.getDescricao());
		
	}
	
	@Test
	@DisplayName("Retorna tipo ordenado por descrição")
	public void RetornaTipoOrdenadoPorDescricao() {
		
		List<Tipo> tipos = this.tipoRepository.findByOrderByDescricao();
		assertEquals(this.descricao2, tipos.get(0).getDescricao());
		assertEquals(this.descricao1, tipos.get(1).getDescricao());
		assertEquals(this.descricao3, tipos.get(2).getDescricao());
		
	}
	
	@Test
	@DisplayName("Informa se tipo já está cadastrado")
	public void InformaSeTipoJaEstaCadastrada() {
		
		boolean retorno1 = this.tipoRepository.existsByDescricao(this.descricao1);
		boolean retorno2 = this.tipoRepository.existsByDescricao("Word");
		
		assertTrue(retorno1);
		assertFalse(retorno2);
		
	}

}
