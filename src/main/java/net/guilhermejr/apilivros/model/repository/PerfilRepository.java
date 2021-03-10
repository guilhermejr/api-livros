package net.guilhermejr.apilivros.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Perfil;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, Long> {

}
