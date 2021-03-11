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

import net.guilhermejr.apilivros.model.entity.Editora;

@DataJpaTest
public class EditoraRepositoryTest {
	
	@Autowired
	private EditoraRepository editoraRepository;
	
	private String descricao1 = "Nerdbooks";
	private String descricao2 = "Darkside";
	private String descricao3 = "Intrínsica";
	
	@BeforeEach
	private void criaEditoras() {
		
		Editora editoraCadastro1 = Editora.builder().descricao(this.descricao1).build();
		Editora editoraCadastro2 = Editora.builder().descricao(this.descricao2).build();
		Editora editoraCadastro3 = Editora.builder().descricao(this.descricao3).build();
		this.editoraRepository.saveAll(Arrays.asList(editoraCadastro1, editoraCadastro2, editoraCadastro3));
		
	}

	@Test
	@DisplayName("Busca editora por descrição")
	public void BuscaEditoraPorDescricao() {
		
		Editora editora = this.editoraRepository.findByDescricao(this.descricao2);
		assertNotNull(editora);
		assertEquals(this.descricao2, editora.getDescricao());
		
	}
	
	@Test
	@DisplayName("Retorna editora ordenado por descrição")
	public void RetornaEditoraOrdenadoPorDescricao() {
		
		List<Editora> editoras = this.editoraRepository.findByOrderByDescricao();
		assertEquals(this.descricao2, editoras.get(0).getDescricao());
		assertEquals(this.descricao3, editoras.get(1).getDescricao());
		assertEquals(this.descricao1, editoras.get(2).getDescricao());
		
	}
	
	@Test
	@DisplayName("Informa se editora já está cadastrada")
	public void InformaSeEditoraJaEstaCadastrada() {
		
		boolean retorno1 = this.editoraRepository.existsByDescricao(this.descricao1);
		boolean retorno2 = this.editoraRepository.existsByDescricao("Abril");
		
		assertTrue(retorno1);
		assertFalse(retorno2);
		
	}

}
