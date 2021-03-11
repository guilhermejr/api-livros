package net.guilhermejr.apilivros.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Autor;

@Repository
public interface AutorRepository  extends JpaRepository<Autor, Long> {

	Autor findByDescricao(String descricao);
	
	List<Autor> findByOrderByDescricao();
	
	boolean existsByDescricao(String descricao);

}
