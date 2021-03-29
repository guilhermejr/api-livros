package net.guilhermejr.apilivros.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long>, JpaSpecificationExecutor<Livro> {

	boolean existsByIsbn(String isbn);

	Page<Livro> findByEstanteId(Long estante, Pageable paginacao);

}
