package net.guilhermejr.apilivros.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Idioma;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
	
	Idioma findByDescricao(String descricao);
	
	List<Idioma> findByOrderByDescricao();
	
	boolean existsByDescricao(String descricao);

}
