package net.guilhermejr.apilivros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Long> {
	
	Genero findByDescricao(String descricao);

}
