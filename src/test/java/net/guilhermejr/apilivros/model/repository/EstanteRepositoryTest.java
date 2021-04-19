package net.guilhermejr.apilivros.model.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import net.guilhermejr.apilivros.model.entity.Estante;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class EstanteRepositoryTest {
	
	@Autowired
	private EstanteRepository estanteRepository;
	
	@Test
	@DisplayName("Retorna estante ordenado por descrição")
	public void RetornaEstanteOrdenadoPorDescricao() {
		
		List<Estante> estantes = this.estanteRepository.findByOrderByDescricao();
		assertEquals("Biblioteca", estantes.get(0).getDescricao());
		assertEquals("Desejados", estantes.get(1).getDescricao());
		assertEquals("Doação", estantes.get(2).getDescricao());
		
	}

}
