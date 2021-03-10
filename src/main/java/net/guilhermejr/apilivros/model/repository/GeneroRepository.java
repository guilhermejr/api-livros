package net.guilhermejr.apilivros.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
	
	Genero findByDescricao(String descricao);
	
	List<Genero> findByOrderByDescricao();
	
	boolean existsByDescricao(String generoNome);

}
