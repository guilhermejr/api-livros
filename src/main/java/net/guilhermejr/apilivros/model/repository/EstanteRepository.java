package net.guilhermejr.apilivros.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Estante;

@Repository
public interface EstanteRepository extends JpaRepository<Estante, Long> {

	List<Estante> findByOrderByDescricao();
	
}
