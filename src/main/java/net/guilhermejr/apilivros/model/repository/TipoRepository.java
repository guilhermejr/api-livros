package net.guilhermejr.apilivros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Tipo;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {

}
