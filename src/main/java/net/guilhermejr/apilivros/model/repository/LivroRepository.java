package net.guilhermejr.apilivros.model.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.guilhermejr.apilivros.model.entity.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
	
	Livro findByIsbn(String isbn13);

	Page<Livro> findByTituloContainingIgnoreCase(String titulo, Pageable paginacao);

//	Page<Livro> findByTenho(boolean tenho, Pageable paginacao);
//
//	Page<Livro> findByTenhoAndTituloContainingIgnoreCase(boolean tenho, String titulo, Pageable paginacao);

}
