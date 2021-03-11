package net.guilhermejr.apilivros.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Editora;

@Repository
public interface EditoraRepository extends JpaRepository<Editora, Long> {
	
	Editora findByDescricao(String descricao);
	
	List<Editora> findByOrderByDescricao();
	
	boolean existsByDescricao(String descricao);
}
