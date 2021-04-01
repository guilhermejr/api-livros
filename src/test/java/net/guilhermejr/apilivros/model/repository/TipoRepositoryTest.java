package net.guilhermejr.apilivros.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.guilhermejr.apilivros.model.entity.Tipo;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class TipoRepositoryTest {
	
	@Autowired
	private TipoRepository tipoRepository;	
	
	@Test
	@DisplayName("Retorna tipo ordenado por descrição")
	public void RetornaTipoOrdenadoPorDescricao() {
		
		List<Tipo> tipos = this.tipoRepository.findByOrderByDescricao();
		assertEquals("E-book", tipos.get(0).getDescricao());
		assertEquals("Físico", tipos.get(1).getDescricao());
		assertEquals("PDF", tipos.get(2).getDescricao());
		
	}

}
